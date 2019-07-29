package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenArray2D;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.factory.IGenSimpleStorage;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates two dimension arrays based on field type
 *
 * @author GoodforGod
 * @see GenArray2D
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @see CollectionComplexGenerator
 * @since 04.11.2018
 */
public class Array2DComplexGenerator extends ArrayComplexGenerator {

    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final IGenSimpleStorage storage,
                           final int depth) {
        if (field == null)
            return null;

        final Class<?> valueClass = field.getType().getComponentType().getComponentType();
        if (annotation == null) {
            final int sizeFirst = ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT);
            final int sizeSecond = ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT);
            final Class<? extends IGenerator> suitable = storage.getSuitable(field, valueClass);
            return genArray2D(sizeFirst, sizeSecond, valueClass, suitable, storage, depth, 1);
        }

        final GenArray2D a = ((GenArray2D) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? storage.getSuitable(field, valueClass)
                : a.value();

        final int sizeFirst = getDesiredSize(a.minFirst(), a.maxFirst(), a.fixedFirst());
        final int sizeSecond = getDesiredSize(a.minSecond(), a.maxSecond(), a.fixedSecond());
        return genArray2D(sizeFirst,
                sizeSecond,
                valueClass,
                generatorClass,
                storage,
                depth,
                a.depth());
    }

    @Override
    public Object generate() {
        final int sizeFirst = ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT);
        final int sizeSecond = ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT);
        return genArray2D(sizeFirst,
                sizeSecond,
                String.class,
                IdGenerator.class,
                null,
                GenEmbedded.MAX,
                1);
    }

    private Object genArray2D(final int rows,
                              final int rowSize,
                              final Class<?> valueClass,
                              final Class<? extends IGenerator> valueGenerator,
                              final IGenSimpleStorage storage,
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
