package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import java.time.LocalDateTime;
import java.time.YearMonth;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author GoodforGod
 * @see YearMonth
 * @since 21.10.2022
 */
public class YearMonthGenerator implements ITimeGenerator<YearMonth> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull YearMonth generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull YearMonth generate(final long minUnix, final long maxUnix) {
        final LocalDateTime localDateTime = generator.generate(minUnix, maxUnix);
        return YearMonth.of(localDateTime.getYear(), localDateTime.getMonth());
    }
}
