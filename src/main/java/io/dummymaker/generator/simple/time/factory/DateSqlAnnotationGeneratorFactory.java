package io.dummymaker.generator.simple.time.factory;

import io.dummymaker.annotation.simple.time.GenDateSql;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.time.DateSqlGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.01.2023
 */
public final class DateSqlAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenDateSql> {

    @Override
    public @NotNull Generator<?> get(GenDateSql annotation) {
        return new DateSqlGenerator(annotation.from(), annotation.to());
    }
}
