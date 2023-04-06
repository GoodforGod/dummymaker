package io.goodforgod.dummymaker.generator.simple.time.factory;

import io.goodforgod.dummymaker.annotation.simple.time.GenOffsetDateTime;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.time.OffsetDateTimeGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class OffsetDateTimeAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenOffsetDateTime> {

    @Override
    public @NotNull Generator<?> get(GenOffsetDateTime annotation) {
        return new OffsetDateTimeGenerator(annotation.from(), annotation.to());
    }
}
