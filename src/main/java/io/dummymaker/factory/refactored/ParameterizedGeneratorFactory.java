package io.dummymaker.factory.refactored;

import java.lang.annotation.Annotation;

/**
 * @see ParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public interface ParameterizedGeneratorFactory<A extends Annotation> {

    ParameterizedGenerator<?> get(A annotation);
}
