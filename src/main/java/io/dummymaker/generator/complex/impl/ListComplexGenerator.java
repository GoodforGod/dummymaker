package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.container.impl.GeneratorsStorage;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicCollectionUtils.generateRandomAmount;

/**
 * Generates List for GenList annotation
 *
 * @see GenList
 *
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
                           final GeneratorsStorage storage) {
        if (field == null || !field.getType().isAssignableFrom(List.class))
            return null;

        final Class<?> valueClass = (Class<?>) getGenericType(field.getGenericType());
        if(annotation == null) {
            if(storage == null)
                return Collections.emptyList();

            return generateList(10,
                    storage.getRandomGenInstance(field).getClass(),
                    ((Class<?>) valueClass),
                    storage);
        }

        final GenList a = ((GenList) annotation);
        final int amount = generateRandomAmount(a.min(), a.max(), a.fixed()); // due to initial object

        return generateList(amount, a.value(), ((Class<?>) valueClass), storage);
    }

    @Override
    public Object generate() {
        return generateList(10,
                IdGenerator.class,
                Object.class,
                null);
    }
}
