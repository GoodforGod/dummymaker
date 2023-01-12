package io.dummymaker.generator.complex;

import static io.dummymaker.util.CastUtils.getGenericType;

import io.dummymaker.annotation.GenDepth;
import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.factory.old.GenStorage;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.string.IdGenerator;
import io.dummymaker.util.RandomUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generates Set or GenSet annotation
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenSet
 * @see ComplexGenerator
 * @see CollectionComplexGenerator
 * @since 21.04.2018
 */
public class SetComplexGenerator extends CollectionComplexGenerator {

    @SuppressWarnings("Duplicates")
    @Override
    public @Nullable Object generate(final @NotNull Class<?> parent,
                                     final @NotNull Field field,
                                     final @NotNull GenStorage storage,
                                     final Annotation annotation,
                                     final int depth) {
        if (!Set.class.isAssignableFrom(field.getType()))
            return null;

        final Class<?> valueClass = (Class<?>) getGenericType(field.getGenericType());
        if (annotation == null) {
            final int size = RandomUtils.random(MIN_DEFAULT, MAX_DEFAULT);
            final Class<? extends Generator> suitable = suitable(storage, field, valueClass);
            final int maxDepth = storage.getDepth(parent, field.getType());
            return genCollection(size, buildCollection(field, size), suitable, valueClass, storage, depth, maxDepth);
        }

        final GenSet a = ((GenSet) annotation);
        final Class<? extends Generator> generatorClass = isGenDefault(a.value())
                ? suitable(storage, field, valueClass)
                : a.value();

        final int size = getDesiredSize(a.min(), a.max(), a.fixed());
        return genCollection(size, buildCollection(field, size), generatorClass, valueClass, storage, depth, GenDepth.DEFAULT);
    }

    @Override
    public @NotNull Object get() {
        final int size = RandomUtils.random(MIN_DEFAULT, MAX_DEFAULT);
        final Set collection = buildCollection(null, size);
        return genCollection(size, collection, IdGenerator.class, Object.class, null, GenDepth.MAX, 1);
    }

    private @NotNull Set buildCollection(Field field, int size) {
        if (field == null)
            return new HashSet<>(size);

        if (TreeSet.class.equals(field.getType())) {
            return new TreeSet<>();
        } else if (ConcurrentSkipListSet.class.equals(field.getType())) {
            return new ConcurrentSkipListSet<>();
        } else if (LinkedHashSet.class.equals(field.getType())) {
            return new LinkedHashSet<>(size);
        } else if (CopyOnWriteArraySet.class.equals(field.getType())) {
            return new CopyOnWriteArraySet<>();
        }

        return new HashSet<>(size);
    }
}
