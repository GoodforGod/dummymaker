package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.generator.simple.impl.string.IdGenerator;
import io.dummymaker.util.BasicCastUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

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

    public SetComplexGenerator() {
        super(new IdGenerator());
    }

    @Override
    public Object generate(final Annotation annotation,
                           final Field field) {
        if (field == null || annotation == null || !field.getType().isAssignableFrom(Set.class))
            return null;

        final GenSet a = ((GenSet) annotation);
        final Type valueClass = BasicCastUtils.getGenericType(field);
        final int amount = generateRandomAmount(a.min(), a.max(), a.fixed()); // due to initial object

        return new HashSet<>(generateList(amount, a.value(), ((Class<?>) valueClass)));
    }

    @Override
    public Object generate() {
        return generateList(10, null, Object.class);
    }
}
