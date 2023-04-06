package io.goodforgod.dummymaker.generator.simple.number.factory;

import io.goodforgod.dummymaker.annotation.simple.number.GenSequence;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.number.SequenceGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @see GenSequence
 * @see SequenceGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class SequenceAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenSequence> {

    @Override
    public @NotNull Generator<Long> get(GenSequence annotation) {
        return new SequenceGenerator(annotation.from());
    }
}
