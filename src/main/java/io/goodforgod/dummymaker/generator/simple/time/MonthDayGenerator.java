package io.goodforgod.dummymaker.generator.simple.time;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.Generator;
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
public final class MonthDayGenerator implements Generator<MonthDay> {

    private final LocalDateTimeGenerator localDateTimeGenerator;

    public MonthDayGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public MonthDayGenerator(long from, long to) {
        this.localDateTimeGenerator = new LocalDateTimeGenerator(from, to);
    }

    @Override
    public @NotNull MonthDay get() {
        final LocalDateTime localDateTime = localDateTimeGenerator.get();
        return MonthDay.of(localDateTime.getMonth(), localDateTime.getDayOfMonth());
    }
}
