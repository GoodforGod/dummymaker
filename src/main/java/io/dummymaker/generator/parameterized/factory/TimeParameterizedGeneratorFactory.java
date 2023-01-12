package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.TimeParameterizedGenerator;

/**
 * @see GenTime
 * @see TimeParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class TimeParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenTime> {

    @Override
    public ParameterizedGenerator<?> get(GenTime annotation) {
        return new TimeParameterizedGenerator(annotation.from(), annotation.to(), annotation.formatter());
    }
}
