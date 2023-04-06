package io.goodforgod.dummymaker.generator.simple.time.factory;

import io.goodforgod.dummymaker.annotation.simple.time.GenDuration;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.time.DurationGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 27.03.2023
 */
public final class DurationAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenDuration> {

    @Override
    public @NotNull Generator<?> get(GenDuration annotation) {
        return new DurationGenerator(annotation.min(), annotation.max());
    }
}
