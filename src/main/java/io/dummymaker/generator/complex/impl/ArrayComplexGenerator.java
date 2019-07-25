package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenArray;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.factory.IGenSimpleStorage;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

import static io.dummymaker.util.GenUtils.getAutoGenerator;

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
                           final IGenSimpleStorage storage,
                           final int depth) {
        if (field == null)
            return null;

        final Class<?> valueClass = field.getType().getComponentType();
        if (annotation == null) {
            return genArray(ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT),
                    valueClass,
                    getAutoGenerator(field,valueClass),
                    storage,
                    depth,
                    1);
        }

        final GenArray a = ((GenArray) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? getAutoGenerator(field,valueClass)
                : a.value();

        final int size = getDesiredSize(a.min(), a.max(), a.fixed());
        return genArray(size, valueClass, generatorClass, storage, depth, a.depth());
    }

    @Override
    public Object generate() {
        return genArray(ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT),
                String.class,
                IdGenerator.class,
                null,
                GenEmbedded.MAX,
                1);
    }

    Object genArray(final int size,
                    final Class<?> valueClass,
                    final Class<? extends IGenerator> valueGenerator,
                    final IGenSimpleStorage storage,
                    final int depth,
                    final int maxDepth) {

        // Firstly try to generate initial object, so we won't allocate list if not necessary
        final Object initial = generateValue(valueGenerator, valueClass, storage, depth, maxDepth);
        if (initial == null) {
            return null;
        }

        final Object array = Array.newInstance(valueClass, size);
        Array.set(array, 0, initial);
        for (int i = 1; i < size; i++) {
            final Object o = generateValue(valueGenerator, valueClass, storage, depth, maxDepth);
            Array.set(array, i, o);
        }

        return array;
    }
}
