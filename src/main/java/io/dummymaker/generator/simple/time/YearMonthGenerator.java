package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.TimeGenerator;
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
public final class YearMonthGenerator implements TimeGenerator<YearMonth> {

    private static final LocalDateTimeGenerator GENERATOR = new LocalDateTimeGenerator();

    @Override
    public @NotNull YearMonth get() {
        return get(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull YearMonth get(long fromUnixTime, long toUnixTime) {
        final LocalDateTime localDateTime = GENERATOR.get(fromUnixTime, toUnixTime);
        return YearMonth.of(localDateTime.getYear(), localDateTime.getMonth());
    }
}
