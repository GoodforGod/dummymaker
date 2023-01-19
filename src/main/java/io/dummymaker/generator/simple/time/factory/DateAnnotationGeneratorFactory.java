package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenDate;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.DateGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class DateAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenDate> {

    @Override
    public @NotNull Generator<?> get(GenDate annotation) {
        return new DateGenerator(annotation.from(), annotation.to());
    }
}
