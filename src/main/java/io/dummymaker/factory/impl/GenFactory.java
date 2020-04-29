package io.dummymaker.factory.impl;

import io.dummymaker.annotation.complex.*;
import io.dummymaker.annotation.special.GenCustom;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.factory.IGenFactory;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.model.GenContainer;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import io.dummymaker.model.error.GenException;
import io.dummymaker.scan.IGenAutoScanner;
import io.dummymaker.scan.impl.GenRuledScanner;
import io.dummymaker.util.CastUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.dummymaker.generator.simple.EmbeddedGenerator.toDepth;
import static io.dummymaker.util.CastUtils.castObject;
import static io.dummymaker.util.CastUtils.instantiate;
import static io.dummymaker.util.CollectionUtils.isEmpty;

/**
 * Produce data object objects and fill their fields with data Successor of
 * initial PopulateFactory
 *
 * @author GoodforGod
 * @see IGenerator
 * @see IComplexGenerator
 * @see io.dummymaker.annotation.core.PrimeGen
 * @see io.dummymaker.annotation.core.ComplexGen
 * @see IGenFactory
 * @since 21.07.2019
 */
public class GenFactory implements IGenFactory {

    private final GenRules rules;
    private final IGenAutoScanner scanner;

    public GenFactory() {
        this(Collections.emptyList());
    }

    public GenFactory(@Nullable GenRule... rules) {
        this(Arrays.asList(rules));
    }

    public GenFactory(@NotNull Collection<GenRule> rules) {
        this(isEmpty(rules) ? null : GenRules.of(rules));
    }

    public GenFactory(@Nullable GenRules rules) {
        this.rules = rules;
        this.scanner = new GenRuledScanner(new GenSupplier(), rules);
    }

    @Nullable
    @Override
    public <T> T build(@Nullable Class<T> target) {
        return fill(instantiate(target));
    }

    @Nullable
    @Override
    public <T> T build(@NotNull Supplier<T> supplier) {
        return fill(supplier.get());
    }

    @NotNull
    @Override
    public <T> List<T> build(@Nullable Class<T> target, int amount) {
        return stream(target, amount).collect(Collectors.toList());
    }

    @NotNull
    @Override
    public <T> List<T> build(@NotNull Supplier<T> supplier, int amount) {
        return stream(supplier, amount).collect(Collectors.toList());
    }

    @NotNull
    @Override
    public <T> Stream<T> stream(@Nullable Class<T> target, int amount) {
        if (amount < 1 || instantiate(target) == null)
            return Stream.empty();

        final Stream<T> stream = IntStream.range(0, amount).mapToObj(i -> instantiate(target));
        return fill(stream);
    }

    @NotNull
    @Override
    public <T> Stream<T> stream(@NotNull Supplier<T> supplier, int amount) {
        if (supplier.get() == null)
            return Stream.empty();

        final Stream<T> stream = IntStream.range(0, amount).mapToObj(i -> supplier.get());
        return fill(stream);
    }

    @Nullable
    @Override
    public <T> T fill(@Nullable T t) {
        if (t == null)
            return null;

        final GenStorage storage = new GenStorage(scanner, rules);
        return fillEntity(t, storage, 1);
    }

    @NotNull
    @Override
    public <T> Stream<T> fill(@Nullable Stream<T> stream) {
        if (stream == null)
            return Stream.empty();

        final GenStorage storage = new GenStorage(scanner, rules);
        return stream.filter(Objects::nonNull)
                .map(t -> fillEntity(t, storage, 1));
    }

    @NotNull
    @Override
    public <T> List<T> fill(@Nullable Collection<T> collection) {
        return isEmpty(collection)
                ? Collections.emptyList()
                : fill(collection.stream()).collect(Collectors.toList());
    }

    /**
     * Populate single entity
     *
     * @param t       entity to populate
     * @param storage gen factory util class
     * @param depth   current embedded depth level
     * @return populated entity
     */
    @Nullable
    <T> T fillEntity(@Nullable T t, GenStorage storage, int depth) {
        if (t == null)
            return null;

        final Map<Field, GenContainer> containers = storage.getContainers(t);

        final List<Map.Entry<Field, GenContainer>> unmarked = containers.entrySet().stream()
                .filter(e -> storage.isUnmarked(e.getKey()))
                .collect(Collectors.toList());

        for (Map.Entry<Field, GenContainer> e : unmarked) {
            final GenContainer container = e.getValue();
            final Field field = e.getKey();
            try {
                field.setAccessible(true);
                final Object generated = generateObject(t.getClass(), field, container, storage, depth);
                if (generated != null)
                    field.set(t, generated);

            } catch (Exception ex) {
                throw new GenException(ex);
            }
        }

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
        final IGenerator<?> generator = container.haveGeneratorExample()
                ? container.getGeneratorExample()
                : storage.getGenerator(container.getGenerator());

        final Annotation annotation = container.getMarker();

        Object generated;

        final boolean isEmbedded = EmbeddedGenerator.class.equals(container.getGenerator());
        if (isEmbedded) {
            generated = generateEmbeddedObject(target, field, container, storage, depth);
        } else if (storage.isSequential(target, field)) {
            generated = generateSequenceObject(field, storage.getSequential(target, field));
        } else if (container.isComplex() && generator instanceof IComplexGenerator) {
            // If complexGen can generate embedded objects
            // And not handling it like BasicComplexGenerator, you are probably
            // StackOverFlowed
            generated = ((IComplexGenerator) generator).generate(target, field, storage, annotation, depth);
        } else {
            generated = generator.generate();
        }

        final Object casted = castObject(generated, field.getType());
        if (!isEmbedded && casted == null)
            storage.markNullable(field);

        return casted;
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
        final int fieldDepth = getDepth(parent, type, container.getMarker(), storage);
        if (fieldDepth <= depth)
            return null;

        final Object embedded = instantiate(type);
        final Object entity = fillEntity(embedded, storage, depth + 1);
        if (entity == null) {
            storage.markNullable(field);
            return null;
        }

        return entity;
    }

    /**
     * Generate sequence number fields next value
     */
    private Object generateSequenceObject(Field field, IGenerator<?> generator) {
        return CastUtils.castToNumber(generator.generate(), field.getType());
    }

    /**
     * Calculates allowed depth level
     *
     * @param annotation target
     * @return allowed depth level
     * @see GenEmbedded
     */
    private int getDepth(final Class<?> parent,
                         final Class<?> target,
                         final Annotation annotation,
                         final GenStorage storage) {
        if (annotation != null) {
            if (annotation.annotationType().equals(GenEmbedded.class)) {
                return toDepth(((GenEmbedded) annotation).depth());
            } else if (annotation.annotationType().equals(GenCustom.class)) {
                return toDepth(((GenCustom) annotation).depth());
            } else if (annotation.annotationType().equals(GenList.class)) {
                return toDepth(((GenList) annotation).depth());
            } else if (annotation.annotationType().equals(GenSet.class)) {
                return toDepth(((GenSet) annotation).depth());
            } else if (annotation.annotationType().equals(GenMap.class)) {
                return toDepth(((GenMap) annotation).depth());
            } else if (annotation.annotationType().equals(GenArray.class)) {
                return toDepth(((GenArray) annotation).depth());
            } else if (annotation.annotationType().equals(GenArray2D.class)) {
                return toDepth(((GenArray2D) annotation).depth());
            }
        }

        return toDepth(storage.getDepth(parent, target));
    }
}
