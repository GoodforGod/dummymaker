package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.UnixTimeGenerator;
import java.time.LocalDateTime;
import java.time.MonthDay;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see MonthDay
 * @since 21.10.2022
 */
public final class MonthDayGenerator implements UnixTimeGenerator<MonthDay> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull MonthDay get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull MonthDay generate(final long fromUnixTime, final long toUnixTime) {
        final LocalDateTime localDateTime = generator.generate(fromUnixTime, toUnixTime);
        return MonthDay.of(localDateTime.getMonth(), localDateTime.getDayOfMonth());
    }
}
