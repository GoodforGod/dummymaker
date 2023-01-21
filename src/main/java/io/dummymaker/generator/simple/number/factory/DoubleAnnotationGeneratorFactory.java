package io.dummymaker.generator.simple.number.factory;

import io.dummymaker.annotation.simple.number.GenDouble;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.number.DoubleGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.01.2023
 */
public final class DoubleAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenDouble> {

    @Override
    public @NotNull Generator<?> get(GenDouble annotation) {
        return new DoubleGenerator(annotation.from(), annotation.to());
    }
}
