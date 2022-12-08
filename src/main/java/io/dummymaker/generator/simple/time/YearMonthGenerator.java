package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.UnixTimeGenerator;
import java.time.LocalDateTime;
import java.time.YearMonth;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see YearMonth
 * @since 21.10.2022
 */
public final class YearMonthGenerator implements UnixTimeGenerator<YearMonth> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull YearMonth get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull YearMonth generate(final long fromUnixTime, final long toUnixTime) {
        final LocalDateTime localDateTime = generator.generate(fromUnixTime, toUnixTime);
        return YearMonth.of(localDateTime.getYear(), localDateTime.getMonth());
    }
}
