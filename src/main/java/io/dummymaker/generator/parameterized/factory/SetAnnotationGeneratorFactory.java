package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.parameterized.GenSet;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.parameterized.SetParameterizedGenerator;
import io.dummymaker.util.CastUtils;
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
