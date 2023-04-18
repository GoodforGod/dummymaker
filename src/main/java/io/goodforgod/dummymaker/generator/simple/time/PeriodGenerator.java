package io.goodforgod.dummymaker.generator.simple.time;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.Generator;
import java.time.LocalDate;
import java.time.Period;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 09.04.2023
 */
public final class PeriodGenerator implements Generator<Period> {

    private final LocalDateGenerator localDateGenerator;

    public PeriodGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public PeriodGenerator(long from, long to) {
        this.localDateGenerator = new LocalDateGenerator(from, to);
    }

    @Override
    public Period get() {
        final LocalDate date1 = localDateGenerator.get();
        final LocalDate date2 = localDateGenerator.get();
        return (date2.isAfter(date1))
                ? Period.between(date1, date2)
                : Period.between(date2, date1);
    }
}
