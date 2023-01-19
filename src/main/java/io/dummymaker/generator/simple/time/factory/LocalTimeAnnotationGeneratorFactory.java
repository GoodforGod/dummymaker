package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenLocalTime;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.LocalTimeGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class LocalTimeAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenLocalTime> {

    @Override
    public @NotNull Generator<?> get(GenLocalTime annotation) {
        return new LocalTimeGenerator(annotation.from(), annotation.to());
    }
}
