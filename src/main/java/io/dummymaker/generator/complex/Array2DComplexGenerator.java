package io.dummymaker.generator.complex;

import io.dummymaker.annotation.complex.GenArray2D;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.simple.string.IdGenerator;
import io.dummymaker.util.CollectionUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generates two dimension arrays based on field type
 *
 * @author GoodforGod
 * @see GenArray2D
 * @see IComplexGenerator
 * @see CollectionComplexGenerator
 * @since 04.11.2018
 */
public class Array2DComplexGenerator extends ArrayComplexGenerator {

    @Override
    public @Nullable Object generate(final @NotNull Class<?> parent,
                                     final @NotNull Field field,
                                     final @NotNull IGenStorage storage,
                                     final Annotation annotation,
                                     final int depth) {
        if (!field.getType().getTypeName().endsWith("[][]"))
            return null;

        final Class<?> valueClass = field.getType().getComponentType().getComponentType();
        if (annotation == null) {
            final int sizeFirst = CollectionUtils.random(MIN_DEFAULT, MAX_DEFAULT);
            final int sizeSecond = CollectionUtils.random(MIN_DEFAULT, MAX_DEFAULT);
            final Class<? extends IGenerator> suitable = suitable(storage, field, valueClass);
            final int maxDepth = storage.getDepth(parent, valueClass);
            return genArray2D(sizeFirst, sizeSecond, valueClass, suitable, storage, depth, maxDepth);
        }

        final GenArray2D a = ((GenArray2D) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? suitable(storage, field, valueClass)
                : a.value();

        final int sizeFirst = getDesiredSize(a.minFirst(), a.maxFirst(), a.fixedFirst());
        final int sizeSecond = getDesiredSize(a.minSecond(), a.maxSecond(), a.fixedSecond());
        return genArray2D(sizeFirst, sizeSecond, valueClass, generatorClass, storage, depth, a.depth());
    }

    @Override
    public @NotNull Object generate() {
        final int sizeFirst = CollectionUtils.random(MIN_DEFAULT, MAX_DEFAULT);
        final int sizeSecond = CollectionUtils.random(MIN_DEFAULT, MAX_DEFAULT);
        return genArray2D(sizeFirst, sizeSecond, String.class, IdGenerator.class, null, GenEmbedded.MAX, 1);
    }

    private @NotNull Object genArray2D(final int rows,
                                       final int rowSize,
                                       final Class<?> valueClass,
                                       final Class<? extends IGenerator> valueGenerator,
                                       final IGenStorage storage,
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
