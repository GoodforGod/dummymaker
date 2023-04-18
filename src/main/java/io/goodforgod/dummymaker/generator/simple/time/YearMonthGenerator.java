package io.goodforgod.dummymaker.generator.simple.time;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.Generator;
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
public final class YearMonthGenerator implements Generator<YearMonth> {

    private final LocalDateTimeGenerator localDateTimeGenerator;

    public YearMonthGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public YearMonthGenerator(long from, long to) {
        this.localDateTimeGenerator = new LocalDateTimeGenerator(from, to);
    }

    @Override
    public @NotNull YearMonth get() {
        final LocalDateTime localDateTime = localDateTimeGenerator.get();
        return YearMonth.of(localDateTime.getYear(), localDateTime.getMonth());
    }
}
