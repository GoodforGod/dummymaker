package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.parameterized.GenTime;
import io.dummymaker.generator.Generator;
import java.sql.Date;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Sql Time from 1970 to 3000 Year
 *
 * @author Anton Kurako (GoodforGod)
 * @see Date
 * @since 10.03.2019
 */
public final class DateSqlGenerator implements Generator<Date> {

    private final LocalDateGenerator localDateGenerator;

    public DateSqlGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public DateSqlGenerator(long from, long to) {
        this.localDateGenerator = new LocalDateGenerator(from, to);
    }

    @Override
    public @NotNull Date get() {
        return Date.valueOf(localDateGenerator.get());
    }
}
