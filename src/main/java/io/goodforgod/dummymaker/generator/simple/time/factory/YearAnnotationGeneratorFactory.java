package io.goodforgod.dummymaker.generator.simple.time.factory;

import io.goodforgod.dummymaker.annotation.simple.time.GenYear;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.time.YearGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class YearAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenYear> {

    @Override
    public @NotNull Generator<?> get(GenYear annotation) {
        return new YearGenerator(annotation.from(), annotation.to());
    }
}
