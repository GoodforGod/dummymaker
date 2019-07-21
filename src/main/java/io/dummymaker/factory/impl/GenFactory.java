package io.dummymaker.factory.impl;

import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.factory.IGenFactory;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.scan.IPopulateScanner;
import io.dummymaker.scan.impl.PopulateScanner;
import io.dummymaker.util.BasicCastUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.dummymaker.util.BasicCastUtils.castObject;
import static io.dummymaker.util.BasicCastUtils.instantiate;
import static io.dummymaker.util.BasicCollectionUtils.isEmpty;

/**
 * @see IGenFactory
 *
 * @author GoodforGod
 * @since 21.07.2019
 */
@SuppressWarnings("Duplicates")
public class GenFactory implements IGenFactory {

    private final IPopulateScanner populateScanner;

    public GenFactory() {
        this(new PopulateScanner());
    }

    public GenFactory(IPopulateScanner populateScanner) {
        this.populateScanner = populateScanner;
    }

    @Override
    public <T> T build(Class<T> target) {
        return fill(instantiate(target));
    }

    @Override
    public <T> List<T> build(Class<T> target, int amount) {
        return stream(target, amount).collect(Collectors.toList());
    }

    @Override
    public <T> List<T> build(Supplier<T> supplier, int amount) {
        return stream(supplier, amount).collect(Collectors.toList());
    }

    @Override
    public <T> Stream<T> stream(Class<T> target, int amount) {
        if (amount < 1 || instantiate(target) == null)
            return Stream.empty();

        final Stream<T> stream = IntStream.range(0, amount).mapToObj(i -> instantiate(target));
        return fill(stream);
    }

    @Override
    public <T> Stream<T> stream(Supplier<T> supplier, int amount) {
        final Stream<T> stream = IntStream.range(0, amount).mapToObj(i -> supplier.get());
        return fill(stream);
    }

    @Override
    public <T> T fill(T t) {
        if (t == null)
            return null;

        final GenStorage factoryStorage = new GenStorage(populateScanner);
        return fillEntity(t, factoryStorage, 1);
    }

    @Override
    public <T> Stream<T> fill(Stream<T> stream) {
        if (stream == null)
            return Stream.empty();

        final GenStorage factoryStorage = new GenStorage(populateScanner);
        return stream
                .filter(Objects::nonNull)
                .map(t -> fillEntity(t, factoryStorage, 1));
    }

    @Override
    public <T> List<T> fill(List<T> list) {
        return isEmpty(list)
                ? Collections.emptyList()
                : fill(list.stream()).collect(Collectors.toList());
    }

    /**
     * Populate single entity
     *
     * @param t       entity to populate
     * @param storage gen factory util class
     * @param depth   current embedded depth level
     * @return populated entity
     */
    <T> T fillEntity(T t, GenStorage storage, int depth) {
        final Map<Field, GenContainer> containers = storage.getContainers(t.getClass());
        containers.entrySet().stream()
                .filter(e -> storage.isUnmarked(e.getKey()))
                .forEach(k -> {
                    final GenContainer container = k.getValue();
                    final Field field = k.getKey();
                    try {
                        field.setAccessible(true);
                        final Object objValue = generateObject(t.getClass(), field, container, storage, depth);
                        if (objValue != null)
                            field.set(t, objValue);

                    } catch (Exception e) {
                        field.setAccessible(false);
                        throw new IllegalArgumentException(e);
                    }
                });

        return t;
    }

    /**
     * Generate populate field value
     *
     * @param field     field to populate
     * @param container field populate annotations
     * @param storage   gen factory util class
     * @param depth     current embedded depth
     */
    private Object generateObject(final Class<?> target,
                                  final Field field,
                                  final GenContainer container,
                                  final GenStorage storage,
                                  final int depth) {
        final IGenerator generator = storage.getGenerator(container.getGenerator());
        final Annotation annotation = container.getMarker();

        Object generated;

        if (EmbeddedGenerator.class.equals(container.getGenerator())) {
            generated = generateEmbeddedObject(annotation, field, storage, depth);
        } else if (storage.isSequential(target, field)) {
            generated = generateSequenceObject(field, storage.getSequential(target, field));
        } else if (container.isComplex()) {
            // If complexGen can generate embedded objects
            // And not handling it like BasicComplexGenerator, you are probably StackOverFlowed
            generated = ((IComplexGenerator) generator).generate(annotation, field, storage, depth);
        } else {
            generated = generator.generate();
        }

        final Object casted = castObject(generated, field.getType());
        if (casted == null)
            storage.markNullable(field);

        return casted;
    }

    /**
     * Generate embedded field value
     *
     * @param field   field with embedded value
     * @param storage gen factory util class
     */
    private Object generateEmbeddedObject(Annotation annotation, Field field, GenStorage storage, int depth) {
        final int fieldDepth = getDepth(annotation);
        if (fieldDepth < depth)
            return null;

        final Object embedded = instantiate(field.getType());
        if (embedded == null) {
            storage.markNullable(field);
            return null;
        }

        return fillEntity(embedded, storage, depth + 1);
    }

    /**
     * Generate sequence number fields next value
     */
    private Object generateSequenceObject(Field field, IGenerator generator) {
        return BasicCastUtils.castToNumber(generator.generate(), field.getType());
    }

    private int getDepth(final Annotation annotation) {
        if (annotation == null || !annotation.annotationType().equals(GenEmbedded.class))
            return 1;

        final int fieldDepth = ((GenEmbedded) annotation).depth();
        if (fieldDepth < 1)
            return 1;

        return (fieldDepth > GenEmbedded.MAX)
                ? GenEmbedded.MAX
                : fieldDepth;
    }
}
