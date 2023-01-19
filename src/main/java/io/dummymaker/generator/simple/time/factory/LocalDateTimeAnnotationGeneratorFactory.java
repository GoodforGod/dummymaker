package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenLocalDateTime;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.LocalDateTimeGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class LocalDateTimeAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenLocalDateTime> {

    @Override
    public @NotNull Generator<?> get(GenLocalDateTime annotation) {
        return new LocalDateTimeGenerator(annotation.from(), annotation.to());
    }
}
