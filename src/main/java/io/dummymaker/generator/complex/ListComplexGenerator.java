package io.dummymaker.generator.complex;

import static io.dummymaker.util.CastUtils.getGenericType;

import io.dummymaker.annotation.GenDepth;
import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.factory.old.GenStorage;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.string.IdGenerator;
import io.dummymaker.util.RandomUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generates List for GenList annotation
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenList
 * @see ComplexGenerator
 * @see CollectionComplexGenerator
 * @since 21.04.2018
 */
public class ListComplexGenerator extends CollectionComplexGenerator {

    @SuppressWarnings("Duplicates")
    @Override
    public @Nullable Object generate(final @NotNull Class<?> parent,
                                     final @NotNull Field field,
                                     final @NotNull GenStorage storage,
                                     final Annotation annotation,
                                     final int depth) {
        if (!List.class.isAssignableFrom(field.getType()))
            return null;

        final Class<?> valueClass = (Class<?>) getGenericType(field.getGenericType());
        if (annotation == null) {
            final int size = RandomUtils.random(MIN_DEFAULT, MAX_DEFAULT);
            final Class<? extends Generator> suitable = suitable(storage, field, valueClass);
            final int maxDepth = storage.getDepth(parent, field.getType());
            return genCollection(size, buildCollection(field, size), suitable, valueClass, storage, depth, maxDepth);
        }

        final GenList a = ((GenList) annotation);
        final Class<? extends Generator> generatorClass = isGenDefault(a.value())
                ? suitable(storage, field, valueClass)
                : a.value();

        final int size = getDesiredSize(a.min(), a.max(), a.fixed());
        return genCollection(size, buildCollection(field, size), generatorClass, valueClass, storage, depth, depth);
    }

    @Override
    public @NotNull Object get() {
        final int size = RandomUtils.random(MIN_DEFAULT, MAX_DEFAULT);
        final List<Object> list = buildCollection(null, size);
        return genCollection(size, list, IdGenerator.class, String.class, null, GenDepth.MAX, 1);
    }

    private @NotNull <T> List<T> buildCollection(Field field, int size) {
        if (field == null)
            return new ArrayList<>(size);

        if (LinkedList.class.equals(field.getType())) {
            return new LinkedList<>();
        } else if (CopyOnWriteArrayList.class.equals(field.getType())) {
            return new CopyOnWriteArrayList<>();
        }

        return new ArrayList<>(size);
    }
}
