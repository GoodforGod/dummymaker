package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenEnum;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.parameterized.EnumParameterizedGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @see GenEnum
 * @see EnumParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class EnumAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenEnum> {

    @Override
    public @NotNull ParameterizedGenerator<?> get(GenEnum annotation) {
        return new EnumParameterizedGenerator(annotation.only(), annotation.exclude());
    }
}
