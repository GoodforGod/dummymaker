package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenZonedDateTime;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.ZonedDateTimeGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class ZonedDateTimeAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenZonedDateTime> {

    @Override
    public @NotNull Generator<?> get(GenZonedDateTime annotation) {
        return new ZonedDateTimeGenerator(annotation.from(), annotation.to());
    }
}
