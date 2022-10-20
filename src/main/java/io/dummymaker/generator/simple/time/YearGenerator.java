package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import java.time.Year;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author GoodforGod
 * @see Year
 * @since 21.10.2022
 */
public class YearGenerator implements ITimeGenerator<Year> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull Year generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Year generate(final long minUnix, final long maxUnix) {
        return Year.of(generator.generate(minUnix, maxUnix).getYear());
    }
}
