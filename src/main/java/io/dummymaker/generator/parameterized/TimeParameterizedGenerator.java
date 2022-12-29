package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import io.dummymaker.generator.simple.time.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 02.12.2022
 */
public final class TimeParameterizedGenerator implements ParameterizedGenerator<Object> {

    private static final InstantGenerator INSTANT_GENERATOR = new InstantGenerator();
    private static final LocalDateGenerator LOCAL_DATE_GENERATOR = new LocalDateGenerator();
    private static final LocalDateTimeGenerator LOCAL_DATE_TIME_GENERATOR = new LocalDateTimeGenerator();
    private static final LocalTimeGenerator LOCAL_TIME_GENERATOR = new LocalTimeGenerator();
    private static final OffsetDateTimeGenerator OFFSET_DATE_TIME_GENERATOR = new OffsetDateTimeGenerator();
    private static final OffsetTimeGenerator OFFSET_TIME_GENERATOR = new OffsetTimeGenerator();
    private static final ZonedDateTimeGenerator ZONED_DATE_TIME_GENERATOR = new ZonedDateTimeGenerator();
    private static final ZonedOffsetGenerator ZONED_OFFSET_GENERATOR = new ZonedOffsetGenerator();

    private static final DayOfWeekGenerator DAY_OF_WEEK_GENERATOR = new DayOfWeekGenerator();
    private static final MonthGenerator MONTH_GENERATOR = new MonthGenerator();
    private static final MonthDayGenerator MONTH_DAY_GENERATOR = new MonthDayGenerator();
    private static final YearGenerator YEAR_GENERATOR = new YearGenerator();
    private static final YearMonthGenerator YEAR_MONTH_GENERATOR = new YearMonthGenerator();

    private static final DateGenerator DATE_GENERATOR = new DateGenerator();
    private static final DateSqlGenerator DATE_SQL_GENERATOR = new DateSqlGenerator();
    private static final TimeSqlGenerator TIME_GENERATOR = new TimeSqlGenerator();
    private static final TimestampGenerator TIMESTAMP_GENERATOR = new TimestampGenerator();

    private final long fromUnixTime;
    private final long toUnixTime;
    private final DateTimeFormatter formatter;

    public TimeParameterizedGenerator(long fromUnixTime, long toUnixTime, String formatter) {
        this.fromUnixTime = fromUnixTime;
        this.toUnixTime = toUnixTime;
        this.formatter = DateTimeFormatter.ofPattern(formatter);
    }

    @Override
    public @Nullable Object get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        final Class<?> fieldClass = fieldType.value();
        if (fieldClass.isAssignableFrom(ZoneOffset.class)) {
            return ZONED_OFFSET_GENERATOR.get();
        } else if (fieldClass.isAssignableFrom(DayOfWeek.class)) {
            return DAY_OF_WEEK_GENERATOR.get();
        } else if (fieldClass.isAssignableFrom(Month.class)) {
            return MONTH_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(MonthDay.class)) {
            return MONTH_DAY_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(Year.class)) {
            return YEAR_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(YearMonth.class)) {
            return YEAR_MONTH_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(InstantGenerator.class)) {
            return INSTANT_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(ZonedDateTime.class)) {
            return ZONED_DATE_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(OffsetDateTime.class)) {
            return OFFSET_DATE_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(OffsetTime.class)) {
            return OFFSET_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(LocalDateTime.class)) {
            return LOCAL_DATE_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(LocalDate.class)) {
            return LOCAL_DATE_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(LocalTime.class)) {
            return LOCAL_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(Date.class)) {
            return DATE_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(Timestamp.class)) {
            return TIMESTAMP_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(Time.class)) {
            return TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (fieldClass.isAssignableFrom(java.sql.Date.class)) {
            return DATE_SQL_GENERATOR.get(fromUnixTime, toUnixTime);
        }

        final LocalDateTime dateTime = LOCAL_DATE_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        return dateTime.format(formatter);
    }

    @Override
    public Object get() {
        final LocalDateTime dateTime = LOCAL_DATE_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        return dateTime.format(formatter);
    }
}
