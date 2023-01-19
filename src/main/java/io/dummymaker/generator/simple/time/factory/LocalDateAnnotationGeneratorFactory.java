package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenLocalDate;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.LocalDateGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class LocalDateAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenLocalDate> {

    @Override
    public @NotNull Generator<?> get(GenLocalDate annotation) {
        return new LocalDateGenerator(annotation.from(), annotation.to());
    }
}
