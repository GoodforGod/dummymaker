package io.dummymaker.generator.simple;

import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;

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
    public Object generate(final Class<?> parent,
                           final Field field,
                           final IGenStorage storage,
                           final Annotation annotation,
                           final int depth) {
        return null;
    }
}
