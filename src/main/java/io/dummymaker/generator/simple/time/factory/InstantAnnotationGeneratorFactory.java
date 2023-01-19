package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenInstant;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.InstantGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class InstantAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenInstant> {

    @Override
    public @NotNull Generator<?> get(GenInstant annotation) {
        return new InstantGenerator(annotation.from(), annotation.to());
    }
}
