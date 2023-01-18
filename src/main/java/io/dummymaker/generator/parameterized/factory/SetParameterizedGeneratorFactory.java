package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.SetParameterizedGenerator;
import io.dummymaker.util.CastUtils;

/**
 * @see GenSet
 * @see SetParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class SetParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenSet> {

    @Override
    public ParameterizedGenerator<?> get(GenSet annotation) {
        final Generator<?> generator = annotation.value().equals(Generator.class)
                ? null
                : CastUtils.instantiate(annotation.value());

        return new SetParameterizedGenerator(annotation.min(), annotation.max(), annotation.fixed(), generator);
    }
}
