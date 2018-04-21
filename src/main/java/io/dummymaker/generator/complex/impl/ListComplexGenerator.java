package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.impl.collection.impl.ListGenerator;
import io.dummymaker.util.BasicCastUtils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public class ListComplexGenerator implements IComplexGenerator {

    private final ICollectionGenerator<?> generator = new ListGenerator();

    @Override
    public Object generate(final Annotation annotation,
                           final Class<?> fieldClass) {
        if (fieldClass == null || annotation == null || !fieldClass.isAssignableFrom(List.class))
            return null;

        int fixed = ((GenList) annotation).fixed();
        int min = ((GenList) annotation).min();
        int max = ((GenList) annotation).max();
        if (fixed > 0) {
            min = max = fixed;
        }

        final IGenerator valueGenerator = BasicCastUtils.instantiate(((GenList) annotation).value());
        return generator.generate(valueGenerator, fieldClass, min, max);
    }

    @Override
    public Object generate() {
        return null;
    }
}
