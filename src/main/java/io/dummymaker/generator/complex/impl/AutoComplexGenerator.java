package io.dummymaker.generator.complex.impl;

import io.dummymaker.generator.simple.impl.NullGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 22.04.2018
 */
public class AutoComplexGenerator extends BasicComplexGenerator {

    public AutoComplexGenerator() {
        super(new NullGenerator());
    }

    @Override
    public Object generate(Annotation annotation, Field field) {
        return null;
    }

    @Override
    public Object generate() {
        return null;
    }
}
