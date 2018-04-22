package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.generator.simple.impl.string.IdGenerator;
import io.dummymaker.util.BasicCastUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

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

    public ListComplexGenerator() {
        super(new IdGenerator());
    }

    @Override
    public Object generate(final Annotation annotation,
                           final Field field) {
        if (field == null || annotation == null || !field.getType().isAssignableFrom(List.class))
            return null;

        final GenList a = ((GenList) annotation);
        final Type valueClass = BasicCastUtils.getGenericType(field);
        final int amount = generateRandomAmount(a.min(), a.max(), a.fixed()); // due to initial object

        return generateList(amount, a.value(), ((Class<?>) valueClass));
    }

    @Override
    public Object generate() {
        return generateList(10, null, Object.class);
    }
}
