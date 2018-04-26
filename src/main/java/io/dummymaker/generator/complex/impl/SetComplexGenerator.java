package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.container.impl.GeneratorsStorage;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicCollectionUtils.generateRandomAmount;

/**
 * Generates Set or GenSet annotation
 *
 * @see GenSet
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public class SetComplexGenerator extends CollectionComplexGenerator {

    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final GeneratorsStorage storage) {
        if (field == null || !field.getType().isAssignableFrom(Set.class))
            return null;

        final Type valueClass = getGenericType(field.getGenericType());
        if(annotation == null) {
            if(storage == null)
                return Collections.emptySet();

            return new HashSet<>(generateList(10,
                    storage.getRandomGenInstance(field).getClass(),
                    ((Class<?>) valueClass),
                    storage));
        }

        final GenSet a = ((GenSet) annotation);
        final int amount = generateRandomAmount(a.min(), a.max(), a.fixed()); // due to initial object

        return new HashSet<>(generateList(amount, a.value(), ((Class<?>) valueClass), storage));
    }

    @Override
    public Object generate() {
        return new HashSet<>(generateList(10,
                IdGenerator.class,
                Object.class,
                null));
    }
}
