package io.goodforgod.dummymaker.generator.simple.number.factory;

import io.goodforgod.dummymaker.annotation.simple.number.GenDouble;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.number.DoubleGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.01.2023
 */
public final class DoubleAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenDouble> {

    @Override
    public @NotNull Generator<Double> get(GenDouble annotation) {
        return new DoubleGenerator(annotation.from(), annotation.to());
    }
}
