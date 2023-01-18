package io.dummymaker.generator;

import java.lang.annotation.Annotation;

/**
 * @see ParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public interface ParameterizedGeneratorFactory<A extends Annotation> {

    ParameterizedGenerator<?> get(A annotation);
}
