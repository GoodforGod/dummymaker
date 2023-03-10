package io.dummymaker.generator.simple.string.factory;

import io.dummymaker.annotation.simple.string.GenPhone;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.string.PhoneGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 10.03.2023
 */
public final class PhoneAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenPhone> {

    @Override
    public @NotNull Generator<CharSequence> get(GenPhone annotation) {
        return new PhoneGenerator(annotation.formatted());
    }
}
