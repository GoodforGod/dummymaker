package io.goodforgod.dummymaker.generator.simple.number.factory;

import io.goodforgod.dummymaker.annotation.simple.number.GenLong;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.number.LongGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class LongAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenLong> {

    @Override
    public @NotNull Generator<Long> get(GenLong annotation) {
        return new LongGenerator(annotation.from(), annotation.to());
    }
}
