package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.container.impl.GeneratorsStorage;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicGenUtils.getAutoGenerator;

/**
 * Generates List for GenList annotation
 *
 * @see GenList
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @see CollectionComplexGenerator
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public class ListComplexGenerator extends CollectionComplexGenerator {

    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final GeneratorsStorage storage,
                           final int depth) {
        if (field == null || !field.getType().isAssignableFrom(List.class))
            return null;

        final Class<?> valueClass = (Class<?>) getGenericType(field.getGenericType());
        if (annotation == null) {
            return generateList(ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT),
                    getAutoGenerator(valueClass),
                    ((Class<?>) valueClass),
                    storage,
                    depth,
                    1);
        }

        final GenList a = ((GenList) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? getAutoGenerator(valueClass)
                : a.value();

        final int size = genRandomSize(a.min(), a.max(), a.fixed());
        return generateList(size, generatorClass, ((Class<?>) valueClass), storage, depth, a.depth());
    }

    @Override
    public Object generate() {
        return generateList(ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT),
                IdGenerator.class,
                String.class,
                null,
                GenEmbedded.MAX,
                1);
    }
}
