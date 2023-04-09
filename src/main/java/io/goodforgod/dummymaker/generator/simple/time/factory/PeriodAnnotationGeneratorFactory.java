package io.goodforgod.dummymaker.generator.simple.time.factory;

import io.goodforgod.dummymaker.annotation.simple.time.GenPeriod;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.time.PeriodGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 09.04.2023
 */
public final class PeriodAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenPeriod> {

    @Override
    public @NotNull Generator<?> get(GenPeriod annotation) {
        return new PeriodGenerator(annotation.from(), annotation.to());
    }
}
