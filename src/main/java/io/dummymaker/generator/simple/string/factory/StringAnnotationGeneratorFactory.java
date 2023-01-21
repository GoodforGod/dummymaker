package io.dummymaker.generator.simple.string.factory;

import io.dummymaker.annotation.simple.string.GenString;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.string.StringGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.01.2023
 */
public final class StringAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenString> {

    @Override
    public @NotNull Generator<?> get(GenString annotation) {
        return new StringGenerator(annotation.min(), annotation.max());
    }
}
