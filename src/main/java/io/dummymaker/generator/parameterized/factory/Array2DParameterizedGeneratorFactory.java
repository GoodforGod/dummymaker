package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenArray;
import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.ArrayParameterizedGenerator;
import io.dummymaker.generator.parameterized.ListParameterizedGenerator;

/**
 * @see GenArray
 * @see ListParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class ArrayParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenArray> {

    @Override
    public ParameterizedGenerator<?> get(GenArray annotation) {
        return new ArrayParameterizedGenerator(annotation.min(), annotation.max(), annotation.fixed());
    }
}
