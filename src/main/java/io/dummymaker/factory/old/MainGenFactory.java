package io.dummymaker.factory.old;

import static io.dummymaker.util.CastUtils.castObject;
import static io.dummymaker.util.CastUtils.instantiate;
import static io.dummymaker.util.CollectionUtils.isEmpty;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.error.GenException;
import io.dummymaker.generator.complex.ComplexGenerator;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.model.GenContainer;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import io.dummymaker.scan.GenAutoScanner;
import io.dummymaker.scan.impl.GenRuledScanner;
import io.dummymaker.util.CastUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Produce Java Classes and fill their fields with data
 *
 * @author Anton Kurako (GoodforGod)
 * @see Generator
 * @see ComplexGenerator
 * @see GenCustom
 * @see GenFactory
 * @since 21.07.2019
 */
public final class MainGenFactory implements GenFactory {

    private final GenRules rules;
    private final GenAutoScanner scanner;

    public MainGenFactory() {
        this(Collections.emptyList());
    }

    public MainGenFactory(@Nullable GenRule... rules) {
        this(Arrays.asList(rules));
    }

    public MainGenFactory(@NotNull Collection<GenRule> rules) {
        this(isEmpty(rules)
                ? null
                : GenRules.of(rules));
    }

    public MainGenFactory(@Nullable GenRules rules) {
        this.rules = rules;
        this.scanner = new GenRuledScanner(new MainGenSupplier(), rules);
    }

    @Override
    public @Nullable <T> T build(@Nullable Class<T> target) {
        return fill(instantiate(target));
    }

    @Override
    public @Nullable <T> T build(@NotNull Supplier<T> supplier) {
        return fill(supplier.get());
    }

    @Override
    public @NotNull <T> List<T> build(@Nullable Class<T> target, int amount) {
        return stream(target, amount).collect(Collectors.toList());
    }

    @Override
    public @NotNull <T> List<T> build(@NotNull Supplier<T> supplier, int amount) {
        return stream(supplier, amount).collect(Collectors.toList());
    }

    @Override
    public @NotNull <T> Stream<T> stream(@Nullable Class<T> target, long amount) {
        return (target == null)
                ? Stream.empty()
                : stream(() -> instantiate(target), amount);
    }

    @Override
    public @NotNull <T> Stream<T> stream(@NotNull Supplier<T> supplier, long amount) {
        if (supplier.get() == null)
            return Stream.empty();

        final Stream<T> stream = streamInstances(supplier, Math.toIntExact(amount));
        return fill(stream);
    }

    public @Nullable <T> T fill(@Nullable T t) {
        if (t == null)
            return null;

        final MainGenStorage storage = new MainGenStorage(scanner, rules);
        return fillEntity(t, storage, 1);
    }

    public @NotNull <T> Stream<T> fill(@Nullable Stream<T> stream) {
        if (stream == null)
            return Stream.empty();

        final MainGenStorage storage = new MainGenStorage(scanner, rules);
        return fill(stream, storage);
    }

    public @NotNull <T> List<T> fill(@Nullable Collection<T> collection) {
        return isEmpty(collection)
                ? Collections.emptyList()
                : fill(collection.stream()).collect(Collectors.toList());
    }

    private @NotNull <T> Stream<T> streamInstances(@NotNull Supplier<T> supplier, int amount) {
        return IntStream.range(0, amount).mapToObj(o -> supplier.get());
    }

    private @NotNull <T> Stream<T> fill(@NotNull Stream<T> stream, @NotNull MainGenStorage storage) {
        return stream.filter(Objects::nonNull).map(t -> fillEntity(t, storage, 1));
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
    <T> T fillEntity(@Nullable T t, @NotNull MainGenStorage storage, int depth) {
        if (t == null) {
            return null;
        }

        final List<GenContainer> containers = storage.getContainers(t);
        final List<GenContainer> unmarked = containers.stream()
                .filter(e -> storage.isUnmarked(e.getField()))
                .collect(Collectors.toList());

        for (GenContainer container : unmarked) {
            final Field field = container.getField();
            try {
                field.setAccessible(true);
                final Object generated = generateObject(t.getClass(), field, container, storage, depth);
                if (generated != null) {
                    field.set(t, generated);
                }
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
                                  final MainGenStorage storage,
                                  final int depth) {
        final Generator<?> generator = container.getGeneratorExample() != null
                ? container.getGeneratorExample()
                : storage.getGenerator(container.getGenerator());

        final Annotation annotation = container.getMarker();

        Object generated;

        final boolean isEmbedded = EmbeddedGenerator.class.equals(container.getGenerator());
        if (isEmbedded) {
            generated = generateEmbeddedObject(target, field, container, storage, depth);
        } else if (storage.isSequential(target, field)) {
            generated = generateSequenceObject(field, storage.getSequential(target, field));
        } else if (container.isComplex() && generator instanceof ComplexGenerator) {
            // If complexGen can generate embedded objects
            // And not handling it like AbstractComplexGenerator, you are probably StackOverFlowed
            generated = ((ComplexGenerator) generator).generate(target, field, storage, annotation, depth);
        } else {
            generated = generator.get();
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
                                          final MainGenStorage storage,
                                          final int depth) {
        final Class<?> type = field.getType();
        final int fieldDepth = getDepth(parent, type, container.getMarker(), storage);
        if (fieldDepth <= depth) {
            return null;
        }

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
    private Object generateSequenceObject(Field field, Generator<?> generator) {
        return CastUtils.castToNumber(generator.get(), field.getType());
    }

    /**
     * Calculates allowed depth level
     *
     * @param annotation target
     * @return allowed depth level
     */
    private int getDepth(final Class<?> parent,
                         final Class<?> target,
                         final Annotation annotation,
                         final MainGenStorage storage) {
        return EmbeddedGenerator.toDepth(storage.getDepth(parent, target));
    }
}
