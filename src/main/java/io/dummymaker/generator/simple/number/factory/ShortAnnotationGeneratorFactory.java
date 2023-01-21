package io.dummymaker.generator.simple.number.factory;

import io.dummymaker.annotation.simple.number.GenShort;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.number.ShortGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.01.2023
 */
public final class ShortAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenShort> {

    @Override
    public @NotNull Generator<?> get(GenShort annotation) {
        return new ShortGenerator(annotation.from(), annotation.to());
    }
}
