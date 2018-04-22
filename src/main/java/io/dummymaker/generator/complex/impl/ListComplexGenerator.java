package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.generator.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.util.List;

import static io.dummymaker.util.BasicCollectionUtils.generateRandomAmount;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public class ListComplexGenerator extends BasicCollectionComplexGenerator {

    public ListComplexGenerator() {
        super(new IdGenerator());
    }

    @Override
    public Object generate(final Annotation annotation,
                           final Class<?> fieldClass) {
        if (fieldClass == null || annotation == null || !fieldClass.isAssignableFrom(List.class))
            return null;

        final GenList a = ((GenList) annotation);
        final int amount = generateRandomAmount(a.min(), a.max(), a.fixed()) - 1; // due to initial object

        return generateList(amount, a.value(), fieldClass);
    }

    @Override
    public Object generate() {
        return generateList(10, null, Object.class);
    }
}
