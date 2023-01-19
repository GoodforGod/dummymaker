package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenSequence;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.parameterized.SequenceParameterizedGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @see GenSequence
 * @see SequenceParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class SequenceAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenSequence> {

    @Override
    public @NotNull ParameterizedGenerator<?> get(GenSequence annotation) {
        return new SequenceParameterizedGenerator(annotation.from());
    }
}
