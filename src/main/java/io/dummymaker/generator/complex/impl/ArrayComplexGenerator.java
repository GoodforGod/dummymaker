package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenArray;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.container.impl.GeneratorsStorage;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

import static io.dummymaker.util.BasicGenUtils.getAutoGenerator;

/**
 * Generates arrays based on field type
 *
 * @author GoodforGod
 * @see io.dummymaker.annotation.complex.GenArray
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @see CollectionComplexGenerator
 * @since 04.11.2018
 */
public class ArrayComplexGenerator extends CollectionComplexGenerator {

    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final GeneratorsStorage storage,
                           final int depth) {
        if (field == null)
            return null;

        final Class<?> valueClass = field.getType().getComponentType();
        if (annotation == null) {
            return genArray(ThreadLocalRandom.current().nextInt(MIN_COUNT_DEFAULT, MAX_COUNT_DEFAULT),
                    getAutoGenerator(valueClass),
                    ((Class<?>) valueClass),
                    storage,
                    depth,
                    1);
        }

        final GenArray a = ((GenArray) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? getAutoGenerator(valueClass)
                : a.value();

        final int size = getDesiredSize(a.min(), a.max(), a.fixed());
        return genArray(size, generatorClass, ((Class<?>) valueClass), storage, depth, a.depth());
    }

    @Override
    public Object generate() {
        return genArray(ThreadLocalRandom.current().nextInt(MIN_COUNT_DEFAULT, MAX_COUNT_DEFAULT),
                IdGenerator.class,
                String.class,
                null,
                GenEmbedded.MAX,
                1);
    }

    @SuppressWarnings("unchecked")
    <T> T[] genArray(final int size,
                     final Class<? extends IGenerator> valueGenerator,
                     final Class<T> fieldClass,
                     final GeneratorsStorage storage,
                     final int depth,
                     final int maxDepth) {

        // Firstly try to generate initial object, so we won't allocate list if not necessary
        final T initial = generateValue(valueGenerator, fieldClass, storage, depth, maxDepth);
        if (initial == null) {
            return (T[]) new Object[0];
        }

        final T[] array = ((T[]) Array.newInstance(fieldClass, size));
        for (int i = 0; i < size - 1; i++) {
            final T t = generateValue(valueGenerator, fieldClass, storage, depth, maxDepth);
            Array.set(array, i, t);
        }

        return array;
    }
}
