package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.ListParameterizedGenerator;
import io.dummymaker.generator.parameterized.SetParameterizedGenerator;

/**
 * @see GenList
 * @see ListParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class ListParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenList> {

    @Override
    public ParameterizedGenerator<?> get(GenList annotation) {
        return new ListParameterizedGenerator(annotation.min(), annotation.max(), annotation.fixed());
    }
}
