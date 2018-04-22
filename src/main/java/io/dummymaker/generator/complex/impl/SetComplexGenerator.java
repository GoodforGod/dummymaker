package io.dummymaker.generator.complex.impl;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public class SetComplexGenerator extends ListComplexGenerator {

    @Override
    @SuppressWarnings("unchecked")
    public Object generate(final Annotation annotation,
                           final Class<?> fieldClass) {
        return new HashSet<>(((List) super.generate(annotation, fieldClass)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object generate() {
        return new HashSet<>((List) super.generate());
    }
}
