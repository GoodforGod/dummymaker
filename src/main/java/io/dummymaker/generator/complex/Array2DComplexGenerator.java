package io.dummymaker.generator.complex;

import io.dummymaker.annotation.GenDepth;
import io.dummymaker.annotation.complex.GenArray2D;
import io.dummymaker.factory.old.GenStorage;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.string.IdGenerator;
import io.dummymaker.util.RandomUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generates two dimension arrays based on field type
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenArray2D
 * @see ComplexGenerator
 * @see CollectionComplexGenerator
 * @since 04.11.2018
 */
public class Array2DComplexGenerator extends ArrayComplexGenerator {

    @Override
    public @Nullable Object generate(final @NotNull Class<?> parent,
                                     final @NotNull Field field,
                                     final @NotNull GenStorage storage,
                                     final Annotation annotation,
                                     final int depth) {
        if (!field.getType().getTypeName().endsWith("[][]"))
            return null;

        final Class<?> valueClass = field.getType().getComponentType().getComponentType();
        if (annotation == null) {
            final int sizeFirst = RandomUtils.random(MIN_DEFAULT, MAX_DEFAULT);
            final int sizeSecond = RandomUtils.random(MIN_DEFAULT, MAX_DEFAULT);
            final Class<? extends Generator> suitable = suitable(storage, field, valueClass);
            final int maxDepth = storage.getDepth(parent, valueClass);
            return genArray2D(sizeFirst, sizeSecond, valueClass, suitable, storage, depth, maxDepth);
        }

        final GenArray2D a = ((GenArray2D) annotation);
        final Class<? extends Generator> generatorClass = isGenDefault(a.value())
                ? suitable(storage, field, valueClass)
                : a.value();

        final int sizeFirst = getDesiredSize(a.minFirst(), a.maxFirst(), a.fixedFirst());
        final int sizeSecond = getDesiredSize(a.minSecond(), a.maxSecond(), a.fixedSecond());
        return genArray2D(sizeFirst, sizeSecond, valueClass, generatorClass, storage, depth, depth);
    }

    @Override
    public @NotNull Object get() {
        final int sizeFirst = RandomUtils.random(MIN_DEFAULT, MAX_DEFAULT);
        final int sizeSecond = RandomUtils.random(MIN_DEFAULT, MAX_DEFAULT);
        return genArray2D(sizeFirst, sizeSecond, String.class, IdGenerator.class, null, GenDepth.MAX, 1);
    }

    private @NotNull Object genArray2D(final int rows,
                                       final int rowSize,
                                       final Class<?> valueClass,
                                       final Class<? extends Generator> valueGenerator,
                                       final GenStorage storage,
                                       final int depth,
                                       final int maxDepth) {
        final Object array = Array.newInstance(valueClass, rows, rowSize);
        for (int i = 0; i < rows; i++) {
            final Object row = Array.get(array, i);
            final Object objects = genArray(rowSize, valueClass, valueGenerator, storage, depth, maxDepth);
            if (objects == null)
                break;

            final int length = Array.getLength(objects);
            if (length < 1)
                break;

            for (int j = 0; j < length; j++) {
                final Object o = Array.get(objects, j);
                Array.set(row, j, o);
            }
        }

        return array;
    }
}
