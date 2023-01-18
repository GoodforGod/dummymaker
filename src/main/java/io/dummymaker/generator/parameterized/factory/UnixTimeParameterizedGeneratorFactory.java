package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenUnixTime;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.UnixTimeParameterizedGenerator;

/**
 * @see GenUnixTime
 * @see io.dummymaker.generator.parameterized.UnixTimeParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class UnixTimeParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenUnixTime> {

    @Override
    public ParameterizedGenerator<?> get(GenUnixTime annotation) {
        return new UnixTimeParameterizedGenerator(annotation.from(), annotation.to());
    }
}
