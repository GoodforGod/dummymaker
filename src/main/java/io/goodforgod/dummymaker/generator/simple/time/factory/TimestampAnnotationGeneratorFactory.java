package io.goodforgod.dummymaker.generator.simple.time.factory;

import io.goodforgod.dummymaker.annotation.simple.time.GenTimestamp;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.time.TimestampGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class TimestampAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenTimestamp> {

    @Override
    public @NotNull Generator<?> get(GenTimestamp annotation) {
        return new TimestampGenerator(annotation.from(), annotation.to());
    }
}
