package io.dummymaker.generator.simple.string.factory;

import io.dummymaker.annotation.simple.string.GenStringValues;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.string.StringValuesGenerator;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.01.2023
 */
public final class StringValuesAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenStringValues> {

    @Override
    public @NotNull Generator<CharSequence> get(GenStringValues annotation) {
        return new StringValuesGenerator(Arrays.asList(annotation.value()));
    }
}
