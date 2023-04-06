package io.goodforgod.dummymaker.generator.parameterized.factory;

import io.goodforgod.dummymaker.annotation.parameterized.GenArray2D;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.generator.parameterized.Array2DParameterizedGenerator;
import io.goodforgod.dummymaker.util.CastUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @see GenArray2D
 * @see Array2DParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class Array2DAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenArray2D> {

    @Override
    public @NotNull ParameterizedGenerator<?> get(GenArray2D annotation) {
        final Generator<?> generator = annotation.value().equals(Generator.class)
                ? null
                : CastUtils.instantiate(annotation.value());

        return new Array2DParameterizedGenerator(annotation.minFirst(), annotation.maxFirst(), annotation.fixedFirst(),
                annotation.minSecond(), annotation.maxSecond(), annotation.fixedSecond(), generator);
    }
}
