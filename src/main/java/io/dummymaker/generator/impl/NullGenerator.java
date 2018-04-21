package io.dummymaker.generator.impl;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.complex.IComplexGenerator;

import java.lang.annotation.Annotation;

/**
 * Generates null values
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class NullGenerator implements IGenerator<Object>, IComplexGenerator<Object> {

    @Override
    public Object generate() {
        return null;
    }

    @Override
    public Object generate(final Annotation annotation) {
        return null;
    }

    @Override
    public boolean isSuitable(final Annotation annotation) {
        return true;
    }
}
