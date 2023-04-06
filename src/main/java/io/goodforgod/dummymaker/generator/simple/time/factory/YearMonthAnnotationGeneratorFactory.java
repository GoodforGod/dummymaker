package io.goodforgod.dummymaker.generator.simple.time.factory;

import io.goodforgod.dummymaker.annotation.simple.time.GenYearMonth;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.time.YearMonthGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class YearMonthAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenYearMonth> {

    @Override
    public @NotNull Generator<?> get(GenYearMonth annotation) {
        return new YearMonthGenerator(annotation.from(), annotation.to());
    }
}
