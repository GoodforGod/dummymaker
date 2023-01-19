package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenMonthDay;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.MonthDayGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class MonthDayAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenMonthDay> {

    @Override
    public @NotNull Generator<?> get(GenMonthDay annotation) {
        return new MonthDayGenerator(annotation.from(), annotation.to());
    }
}
