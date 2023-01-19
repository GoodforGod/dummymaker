package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenMonth;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.MonthGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class MonthAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenMonth> {

    @Override
    public @NotNull Generator<?> get(GenMonth annotation) {
        return new MonthGenerator(annotation.from(), annotation.to());
    }
}
