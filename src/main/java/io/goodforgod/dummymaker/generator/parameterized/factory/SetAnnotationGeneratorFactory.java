package io.goodforgod.dummymaker.generator.parameterized.factory;

import io.goodforgod.dummymaker.annotation.parameterized.GenSet;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.generator.parameterized.SetParameterizedGenerator;
import io.goodforgod.dummymaker.util.CastUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @see GenSet
 * @see SetParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class SetAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenSet> {

    @Override
    public @NotNull ParameterizedGenerator<?> get(GenSet annotation) {
        final Generator<?> generator = annotation.value().equals(Generator.class)
                ? null
                : CastUtils.instantiate(annotation.value());

        return new SetParameterizedGenerator(annotation.min(), annotation.max(), annotation.fixed(), generator);
    }
}
