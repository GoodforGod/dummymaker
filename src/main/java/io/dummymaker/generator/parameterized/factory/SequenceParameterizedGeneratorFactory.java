package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenSequence;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.SequenceParameterizedGenerator;

/**
 * @see GenSequence
 * @see SequenceParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class SequenceParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenSequence> {

    @Override
    public ParameterizedGenerator<?> get(GenSequence annotation) {
        return new SequenceParameterizedGenerator(annotation.from());
    }
}
