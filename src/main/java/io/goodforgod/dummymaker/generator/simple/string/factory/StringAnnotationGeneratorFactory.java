package io.goodforgod.dummymaker.generator.simple.string.factory;

import io.goodforgod.dummymaker.annotation.simple.string.GenString;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.string.StringGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.01.2023
 */
public final class StringAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenString> {

    @Override
    public @NotNull Generator<CharSequence> get(GenString annotation) {
        return new StringGenerator(annotation.min(), annotation.max());
    }
}
