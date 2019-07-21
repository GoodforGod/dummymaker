package io.dummymaker.generator.simple.impl;

import io.dummymaker.factory.IComplexService;
import io.dummymaker.generator.complex.IComplexGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Generates null values
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class NullGenerator implements IComplexGenerator {

    @Override
    public Object generate() {
        return null;
    }

    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final IComplexService storage,
                           final int depth) {
        return null;
    }
}
