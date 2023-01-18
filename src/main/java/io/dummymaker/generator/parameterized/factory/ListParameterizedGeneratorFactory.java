package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.ListParameterizedGenerator;
import io.dummymaker.util.CastUtils;

/**
 * @see GenList
 * @see ListParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class ListParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenList> {

    @Override
    public ParameterizedGenerator<?> get(GenList annotation) {
        final Generator<?> generator = annotation.value().equals(Generator.class)
                ? null
                : CastUtils.instantiate(annotation.value());

        return new ListParameterizedGenerator(annotation.min(), annotation.max(), annotation.fixed(), generator);
    }
}
