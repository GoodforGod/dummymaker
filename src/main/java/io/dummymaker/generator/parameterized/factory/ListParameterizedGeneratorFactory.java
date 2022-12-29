package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.SetParameterizedGenerator;
import io.dummymaker.generator.parameterized.TimeParameterizedGenerator;

/**
 * @see GenSet
 * @see SetParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class SetParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenSet> {

    @Override
    public ParameterizedGenerator<?> get(GenSet annotation) {
        return new SetParameterizedGenerator(annotation.min(), annotation.max(), annotation.fixed());
    }
}
