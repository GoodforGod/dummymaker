package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.annotation.complex.GenMap;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.ListParameterizedGenerator;
import io.dummymaker.generator.parameterized.MapParameterizedGenerator;

/**
 * @see GenMap
 * @see MapParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class MapParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenMap> {

    @Override
    public ParameterizedGenerator<?> get(GenMap annotation) {
        return new MapParameterizedGenerator(annotation.min(), annotation.max(), annotation.fixed());
    }
}
