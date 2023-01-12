package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenEnum;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.EnumParameterizedGenerator;

/**
 * @see GenEnum
 * @see EnumParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class EnumParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenEnum> {

    @Override
    public ParameterizedGenerator<?> get(GenEnum annotation) {
        return new EnumParameterizedGenerator(annotation.only(), annotation.exclude());
    }
}
