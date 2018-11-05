package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenArray;
import io.dummymaker.annotation.special.GenEmbedded;
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
 * Generates arrays based on field type
 *
 * @see io.dummymaker.annotation.complex.GenArray
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @see CollectionComplexGenerator
 *
 * @author GoodforGod
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
            List<?> objects = generateList(ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT),
                    getAutoGenerator(valueClass),
                    ((Class<?>) valueClass),
                    storage,
                    depth,
                    1);

            return toArray(valueClass, objects);
        }

        final GenArray a = ((GenArray) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? getAutoGenerator(valueClass)
                : a.value();

        final int size = genRandomSize(a.min(), a.max(), a.fixed());
        List<?> objects = generateList(size, generatorClass, ((Class<?>) valueClass), storage, depth, a.depth());
        return toArray(valueClass, objects);
    }

    @Override
    public Object generate() {
        List<String> strings = generateList(ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT),
                IdGenerator.class,
                String.class,
                null,
                GenEmbedded.MAX,
                1);

        return toArray(Object.class, strings);
    }

    private Object toArray(final Class<?> valueClass,
                           final List<?> objects) {
        final Object array = Array.newInstance(valueClass, objects.size());
        for (int i = 0; i < objects.size(); i++)
            Array.set(array, i, objects.get(i));

        return array;
    }
}
