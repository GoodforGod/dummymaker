package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenTimeSql;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.TimeSqlGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class TimeSqlAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenTimeSql> {

    @Override
    public @NotNull Generator<?> get(GenTimeSql annotation) {
        return new TimeSqlGenerator(annotation.from(), annotation.to());
    }
}
