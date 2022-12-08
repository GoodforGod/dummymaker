package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.UnixTimeGenerator;
import java.sql.Date;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Sql Time from 1970 to 3000 Year
 *
 * @author Anton Kurako (GoodforGod)
 * @see Date
 * @since 10.03.2019
 */
public final class DateSqlGenerator implements UnixTimeGenerator<Date> {

    private static final LocalDateGenerator generator = new LocalDateGenerator();

    @Override
    public @NotNull Date get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Date generate(long fromUnixTime, long toUnixTime) {
        return Date.valueOf(generator.generate(fromUnixTime, toUnixTime));
    }
}
