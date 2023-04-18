package io.goodforgod.dummymaker.generator.simple.number.factory;

import io.goodforgod.dummymaker.annotation.parameterized.GenUnixTime;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.number.UnixTimeGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class UnixTimeAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenUnixTime> {

    @Override
    public @NotNull Generator<Long> get(GenUnixTime annotation) {
        return new UnixTimeGenerator(annotation.from(), annotation.to());
    }
}
