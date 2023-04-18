package io.goodforgod.dummymaker.generator.parameterized.factory;

import io.goodforgod.dummymaker.annotation.parameterized.GenList;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.generator.parameterized.ListParameterizedGenerator;
import io.goodforgod.dummymaker.util.CastUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @see GenList
 * @see ListParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class ListAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenList> {

    @Override
    public @NotNull ParameterizedGenerator<?> get(GenList annotation) {
        final Generator<?> generator = annotation.value().equals(Generator.class)
                ? null
                : CastUtils.instantiate(annotation.value());

        return new ListParameterizedGenerator(annotation.min(), annotation.max(), annotation.fixed(), generator);
    }
}
