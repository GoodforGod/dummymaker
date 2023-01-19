package io.dummymaker.generator.simple.number.factory;

import io.dummymaker.annotation.complex.GenUnixTime;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.number.UnixTimeGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class UnixTimeAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenUnixTime> {

    @Override
    public @NotNull Generator<?> get(GenUnixTime annotation) {
        return new UnixTimeGenerator(annotation.from(), annotation.to());
    }
}
