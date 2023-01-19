package io.dummymaker.generator.simple.number.factory;

import io.dummymaker.annotation.simple.number.GenLong;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.number.LongGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class LongAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenLong> {

    @Override
    public @NotNull Generator<?> get(GenLong annotation) {
        return new LongGenerator(annotation.from(), annotation.to());
    }
}
