package io.goodforgod.dummymaker.generator.simple.number.factory;

import io.goodforgod.dummymaker.annotation.simple.number.GenShort;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.number.ShortGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.01.2023
 */
public final class ShortAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenShort> {

    @Override
    public @NotNull Generator<Short> get(GenShort annotation) {
        return new ShortGenerator(annotation.from(), annotation.to());
    }
}
