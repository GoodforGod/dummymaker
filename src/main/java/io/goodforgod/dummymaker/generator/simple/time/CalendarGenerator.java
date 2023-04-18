package io.goodforgod.dummymaker.generator.simple.time;

import io.goodforgod.dummymaker.generator.Generator;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 09.04.2023
 */
public final class CalendarGenerator implements Generator<Calendar> {

    private final TimeZone timeZone;

    public CalendarGenerator() {
        this(TimeZone.getDefault());
    }

    public CalendarGenerator(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public Calendar get() {
        return Calendar.getInstance(timeZone);
    }
}
