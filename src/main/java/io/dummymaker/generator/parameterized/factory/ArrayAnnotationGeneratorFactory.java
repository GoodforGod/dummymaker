package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenArray;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.parameterized.ArrayParameterizedGenerator;
import io.dummymaker.util.CastUtils;
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
