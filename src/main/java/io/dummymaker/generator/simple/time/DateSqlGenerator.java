package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import java.sql.Date;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Sql Time from 1970 to 3000 Year
 *
 * @author GoodforGod
 * @see Date
 * @since 10.03.2019
 */
public class DateSqlGenerator implements ITimeGenerator<Date> {

    private static final LocalDateGenerator generator = new LocalDateGenerator();

    @Override
    public @NotNull Date generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Date generate(long minUnix, long maxUnix) {
        return Date.valueOf(generator.generate(minUnix, maxUnix));
    }
}
