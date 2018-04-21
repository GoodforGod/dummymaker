package io.dummymaker.generator.complex.impl;

import io.dummymaker.generator.complex.IComplexGenerator;

import java.lang.annotation.Annotation;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public class SetComplexGenerator implements IComplexGenerator {

    @Override
    public Object generate(final Annotation annotation,
                        final Class<?> fieldClass) {
        return null;
    }

    @Override
    public Object generate() {
        return null;
    }
}
