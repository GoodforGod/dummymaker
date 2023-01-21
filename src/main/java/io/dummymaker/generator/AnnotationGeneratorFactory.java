package io.dummymaker.generator;

import java.lang.annotation.Annotation;
import org.jetbrains.annotations.NotNull;

/**
 * @see io.dummymaker.annotation.GenCustomFactory
 * @see ParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public interface AnnotationGeneratorFactory<A extends Annotation> {

    /**
     * @param annotation used to construct generator
     * @return generator implementation
     */
    @NotNull
    Generator<?> get(A annotation);
}
