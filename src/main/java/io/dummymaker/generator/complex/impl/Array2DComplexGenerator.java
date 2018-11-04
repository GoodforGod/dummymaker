package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenArray2D;
import io.dummymaker.container.impl.GeneratorsStorage;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.dummymaker.util.BasicGenUtils.getAutoGenerator;

/**
 * Generates two dimension arrays based on field type
 *
 * @see GenArray2D
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @see CollectionComplexGenerator
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class Array2DComplexGenerator extends CollectionComplexGenerator {

    @Override
    public Object generate(Annotation annotation, Field field, GeneratorsStorage storage) {
        if (field == null)
            return null;

        final Class<?> valueClass = field.getType().getComponentType().getComponentType();
        if (annotation == null) {
            final int sizeFirst = ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT);
            final int sizeSecond = ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT);
            return toArray2D(sizeFirst, sizeSecond, valueClass, getAutoGenerator(valueClass), storage);
        }

        final GenArray2D a = ((GenArray2D) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? getAutoGenerator(valueClass)
                : a.value();

        final int sizeFirst = genRandomSize(a.minFirst(), a.maxFirst(), a.fixedFirst());
        final int sizeSecond = genRandomSize(a.minSecond(), a.maxSecond(), a.fixedSecond());
        return toArray2D(sizeFirst, sizeSecond, valueClass, generatorClass, storage);
    }

    @Override
    public Object generate() {
        final int sizeFirst = ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT);
        final int sizeSecond = ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT);
        return toArray2D(sizeFirst, sizeSecond, String.class, IdGenerator.class, null);
    }

    private Object toArray2D(final int firstSize,
                             final int secondSize,
                             final Class<?> valueClass,
                             final Class<? extends IGenerator> valueGenerator,
                             final GeneratorsStorage storage) {
        final Object array = Array.newInstance(valueClass, firstSize, secondSize);
        for (int i = 0; i < firstSize; i++) {
            final Object row = Array.get(array, i);
            final List<?> objects = generateList(secondSize, valueGenerator, valueClass, storage);
            for (int j = 0; j < secondSize; j++)
                Array.set(row, j, objects.get(j));
        }

        return array;
    }
}
