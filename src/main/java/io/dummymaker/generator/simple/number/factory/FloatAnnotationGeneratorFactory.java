package io.dummymaker.generator.simple.number.factory;

import io.dummymaker.annotation.simple.number.GenFloat;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.number.FloatGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.01.2023
 */
public final class FloatAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenFloat> {

    @Override
    public @NotNull Generator<Float> get(GenFloat annotation) {
        return new FloatGenerator(annotation.from(), annotation.to());
    }
}
