package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.UnixTimeGenerator;
import java.time.Year;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see Year
 * @since 21.10.2022
 */
public final class YearGenerator implements UnixTimeGenerator<Year> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull Year get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Year generate(final long minUnix, final long maxUnix) {
        return Year.of(generator.generate(minUnix, maxUnix).getYear());
    }
}
