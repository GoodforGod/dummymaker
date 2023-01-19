package io.dummymaker.generator.simple.number.factory;

import io.dummymaker.annotation.simple.number.GenInt;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.number.IntegerGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class IntegerAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenInt> {

    @Override
    public @NotNull Generator<?> get(GenInt annotation) {
        return new IntegerGenerator(annotation.from(), annotation.to());
    }
}
