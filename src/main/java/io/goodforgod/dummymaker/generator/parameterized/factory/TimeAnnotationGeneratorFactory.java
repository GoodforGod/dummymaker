package io.goodforgod.dummymaker.generator.parameterized.factory;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.generator.parameterized.TimeParameterizedGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @see GenTime
 * @see TimeParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class TimeAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenTime> {

    @Override
    public @NotNull ParameterizedGenerator<?> get(GenTime annotation) {
        return new TimeParameterizedGenerator(annotation.from(), annotation.to(), annotation.formatter());
    }
}
