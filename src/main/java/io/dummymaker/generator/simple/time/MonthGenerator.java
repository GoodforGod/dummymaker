package io.dummymaker.generator.simple.time;

import io.dummymaker.generator.Generator;
import java.time.Month;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see Month
 * @since 21.10.2022
 */
public final class MonthGenerator implements Generator<Month> {

    private final LocalDateTimeGenerator localDateTimeGenerator;

    public MonthGenerator(long from, long to) {
        this.localDateTimeGenerator = new LocalDateTimeGenerator(from, to);
    }

    @Override
    public @NotNull Month get() {
        return localDateTimeGenerator.get().getMonth();
    }
}
