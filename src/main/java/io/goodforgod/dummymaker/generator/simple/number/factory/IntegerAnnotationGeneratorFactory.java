package io.goodforgod.dummymaker.generator.simple.number.factory;

import io.goodforgod.dummymaker.annotation.simple.number.GenInt;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.number.IntegerGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class IntegerAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenInt> {

    @Override
    public @NotNull Generator<Integer> get(GenInt annotation) {
        return new IntegerGenerator(annotation.from(), annotation.to());
    }
}
