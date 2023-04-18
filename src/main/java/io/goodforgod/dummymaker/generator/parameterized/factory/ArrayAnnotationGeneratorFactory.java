package io.goodforgod.dummymaker.generator.parameterized.factory;

import io.goodforgod.dummymaker.annotation.parameterized.GenArray;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.generator.parameterized.ArrayParameterizedGenerator;
import io.goodforgod.dummymaker.util.CastUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @see GenArray
 * @see ArrayParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class ArrayAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenArray> {

    @Override
    public @NotNull ParameterizedGenerator<?> get(GenArray annotation) {
        final Generator<?> generator = annotation.value().equals(Generator.class)
                ? null
                : CastUtils.instantiate(annotation.value());

        return new ArrayParameterizedGenerator(annotation.min(), annotation.max(), annotation.fixed(), generator);
    }
}
