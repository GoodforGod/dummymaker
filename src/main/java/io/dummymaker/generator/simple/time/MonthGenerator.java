package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.TimeGenerator;
import java.time.Month;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see Month
 * @since 21.10.2022
 */
public final class MonthGenerator implements TimeGenerator<Month> {

    private static final LocalDateTimeGenerator GENERATOR = new LocalDateTimeGenerator();

    @Override
    public @NotNull Month get() {
        return get(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Month get(long fromUnixTime, long toUnixTime) {
        return GENERATOR.get(fromUnixTime, toUnixTime).getMonth();
    }
}
