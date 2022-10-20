package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import java.time.Month;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author GoodforGod
 * @see Month
 * @since 21.10.2022
 */
public class MonthGenerator implements ITimeGenerator<Month> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull Month generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Month generate(final long minUnix, final long maxUnix) {
        return generator.generate(minUnix, maxUnix).getMonth();
    }
}
