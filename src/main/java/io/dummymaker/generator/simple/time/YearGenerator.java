package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.TimeGenerator;
import java.time.Year;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see Year
 * @since 21.10.2022
 */
public final class YearGenerator implements TimeGenerator<Year> {

    private static final LocalDateTimeGenerator GENERATOR = new LocalDateTimeGenerator();

    @Override
    public @NotNull Year get() {
        return get(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Year get(long fromUnixTime, long toUnixTime) {
        return Year.of(GENERATOR.get(fromUnixTime, toUnixTime).getYear());
    }
}
