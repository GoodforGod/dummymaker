package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenArray2D;
import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.container.impl.GeneratorsStorage;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicGenUtils.getAutoGenerator;

/**
 * ! NO DESCRIPTION !
 *
 * @see GenArray2D
 *
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

        final Class<?> valueClass = (Class<?>) getGenericType(field.getGenericType());
        if(annotation == null) {
            if(storage == null)
                return Collections.emptyList();

            return generateList(ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT),
                    getAutoGenerator(valueClass),
                    ((Class<?>) valueClass),
                    storage);
        }

        final GenList a = ((GenList) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? getAutoGenerator(valueClass)
                : a.value();

        final int size = genRandomSize(a.min(), a.max(), a.fixed());
        return generateList(size, generatorClass, ((Class<?>) valueClass), storage);
    }

    @Override
    public Object generate() {
        return generateList(ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT),
                IdGenerator.class,
                String.class,
                null);
    }
}
