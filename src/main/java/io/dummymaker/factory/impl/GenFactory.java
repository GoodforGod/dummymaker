package io.dummymaker.factory.impl;

import io.dummymaker.annotation.complex.*;
import io.dummymaker.annotation.special.GenCustom;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.factory.IGenFactory;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.model.GenContainer;
import io.dummymaker.model.GenRules;
import io.dummymaker.model.error.GenException;
import io.dummymaker.scan.IPopulateScanner;
import io.dummymaker.scan.impl.PopulateScanner;
import io.dummymaker.util.CastUtils;

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

import static io.dummymaker.util.CastUtils.castObject;
import static io.dummymaker.util.CastUtils.instantiate;
import static io.dummymaker.util.CollectionUtils.isEmpty;

/**
 * Produce data object objects and fill their fields with data
 * Successor of initial PopulateFactory
 *
 * @author GoodforGod
 * @see IGenerator
 * @see IComplexGenerator
 * @see io.dummymaker.annotation.core.PrimeGen
 * @see io.dummymaker.annotation.core.ComplexGen
 * @see IGenFactory
 * @since 21.07.2019
 */
@SuppressWarnings("Duplicates")
public class GenFactory implements IGenFactory {

    private final GenRules rules;
    private final IPopulateScanner scanner;

    public GenFactory() {
        this(new PopulateScanner());
    }

    public GenFactory(IPopulateScanner scanner) {
        this.scanner = scanner;
        this.rules = null;
    }

    public GenFactory(GenRules rules) {
        this.scanner = new PopulateScanner();
        this.rules = rules;
    }

    public GenFactory(IPopulateScanner scanner, GenRules rules) {
        this.scanner = scanner;
        this.rules = rules;
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

        final GenStorage storage = new GenStorage(scanner, rules);
        return fillEntity(t, storage, 1);
    }

    @Override
    public <T> Stream<T> fill(Stream<T> stream) {
        if (stream == null)
            return Stream.empty();

        final GenStorage storage = new GenStorage(scanner, rules);
        return stream
                .filter(Objects::nonNull)
                .map(t -> fillEntity(t, storage, 1));
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
        if(t == null)
            return null;

        final Map<Field, GenContainer> containers = storage.getContainers(t);

        final List<Map.Entry<Field, GenContainer>> unmarked = containers.entrySet().stream()
                .filter(e -> storage.isUnmarked(e.getKey()))
                .collect(Collectors.toList());

        unmarked.forEach(e -> {
                    final GenContainer container = e.getValue();
                    final Field field = e.getKey();
                    try {
                        field.setAccessible(true);
                        final Object generated = generateObject(t.getClass(), field, container, storage, depth);
                        if (generated != null)
                            field.set(t, generated);
                        else
                            storage.markNullable(field);

                    } catch (Exception ex) {
                        field.setAccessible(false);
                        throw new GenException(ex);
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
            generated = generateEmbeddedObject(target, field, container, storage, depth);
        } else if (storage.isSequential(target, field)) {
            generated = generateSequenceObject(field, storage.getSequential(target, field));
        } else if (container.isComplex()) {
            // If complexGen can generate embedded objects
            // And not handling it like BasicComplexGenerator, you are probably StackOverFlowed
            generated = ((IComplexGenerator) generator).generate(annotation, field, storage, depth);
        } else {
            generated = generator.generate();
        }

        return castObject(generated, field.getType());
    }

    /**
     * Generate embedded field value
     *
     * @param field   field with embedded value
     * @param storage gen factory util class
     */
    private Object generateEmbeddedObject(final Class<?> parent,
                                          final Field field,
                                          final GenContainer container,
                                          final GenStorage storage,
                                          final int depth) {
        final Class<?> type = field.getType();
        final int fieldDepth = getDepth(parent, type, container, storage);
        if (fieldDepth <= depth)
            return null;

        final Object embedded = instantiate(type);
        return fillEntity(embedded, storage, depth + 1);
    }

    /**
     * Generate sequence number fields next value
     */
    private Object generateSequenceObject(Field field, IGenerator generator) {
        return CastUtils.castToNumber(generator.generate(), field.getType());
    }

    /**
     * Calculates allowed depth level
     *
     * @param container target
     * @return allowed depth level
     * @see GenEmbedded
     */
    private int getDepth(final Class<?> parent,
                         final Class<?> target,
                         final GenContainer container,
                         final GenStorage storage) {

        final Annotation annotation = container.getMarker();
        if (annotation != null) {
            if (annotation.annotationType().equals(GenEmbedded.class)) {
                return EmbeddedGenerator.toDepth(((GenEmbedded) annotation).depth());
            } else if (annotation.annotationType().equals(GenCustom.class)) {
                return EmbeddedGenerator.toDepth(((GenCustom) annotation).depth());
            } else if (annotation.annotationType().equals(GenList.class)) {
                return EmbeddedGenerator.toDepth(((GenList) annotation).depth());
            } else if (annotation.annotationType().equals(GenSet.class)) {
                return EmbeddedGenerator.toDepth(((GenSet) annotation).depth());
            } else if (annotation.annotationType().equals(GenMap.class)) {
                return EmbeddedGenerator.toDepth(((GenMap) annotation).depth());
            } else if (annotation.annotationType().equals(GenArray.class)) {
                return EmbeddedGenerator.toDepth(((GenArray) annotation).depth());
            } else if (annotation.annotationType().equals(GenArray2D.class)) {
                return EmbeddedGenerator.toDepth(((GenArray2D) annotation).depth());
            }
        }

        return storage.getDepth(parent, target);
    }
}
