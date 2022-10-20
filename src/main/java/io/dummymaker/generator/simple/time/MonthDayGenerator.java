package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import java.time.LocalDateTime;
import java.time.MonthDay;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author GoodforGod
 * @see MonthDay
 * @since 21.10.2022
 */
public class MonthDayGenerator implements ITimeGenerator<MonthDay> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull MonthDay generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull MonthDay generate(final long minUnix, final long maxUnix) {
        final LocalDateTime localDateTime = generator.generate(minUnix, maxUnix);
        return MonthDay.of(localDateTime.getMonth(), localDateTime.getDayOfMonth());
    }
}
