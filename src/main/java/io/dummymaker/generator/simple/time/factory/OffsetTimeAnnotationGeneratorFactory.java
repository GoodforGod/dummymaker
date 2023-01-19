package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenOffsetTime;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.OffsetTimeGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class OffsetTimeAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenOffsetTime> {

    @Override
    public @NotNull Generator<?> get(GenOffsetTime annotation) {
        return new OffsetTimeGenerator(annotation.from(), annotation.to());
    }
}
