package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.GenType;
import io.dummymaker.factory.GenTypeBuilder;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.simple.time.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.jetbrains.annotations.NotNull;

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
    public Object get(@NotNull GenType fieldType, @NotNull GenTypeBuilder typeBuilder) {
        final Class<?> fieldClass = fieldType.raw();
        if (ZoneOffset.class.isAssignableFrom(fieldClass)) {
            return ZONED_OFFSET_GENERATOR.get();
        } else if (DayOfWeek.class.isAssignableFrom(fieldClass)) {
            return DAY_OF_WEEK_GENERATOR.get();
        } else if (Month.class.isAssignableFrom(fieldClass)) {
            return MONTH_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (MonthDay.class.isAssignableFrom(fieldClass)) {
            return MONTH_DAY_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (Year.class.isAssignableFrom(fieldClass)) {
            return YEAR_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (YearMonth.class.isAssignableFrom(fieldClass)) {
            return YEAR_MONTH_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (Instant.class.isAssignableFrom(fieldClass)) {
            return INSTANT_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (ZonedDateTime.class.isAssignableFrom(fieldClass)) {
            return ZONED_DATE_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (OffsetDateTime.class.isAssignableFrom(fieldClass)) {
            return OFFSET_DATE_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (OffsetTime.class.isAssignableFrom(fieldClass)) {
            return OFFSET_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (LocalDateTime.class.isAssignableFrom(fieldClass)) {
            return LOCAL_DATE_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (LocalDate.class.isAssignableFrom(fieldClass)) {
            return LOCAL_DATE_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (LocalTime.class.isAssignableFrom(fieldClass)) {
            return LOCAL_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (Timestamp.class.isAssignableFrom(fieldClass)) {
            return TIMESTAMP_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (Date.class.isAssignableFrom(fieldClass)) {
            return DATE_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (Time.class.isAssignableFrom(fieldClass)) {
            return TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        } else if (java.sql.Date.class.isAssignableFrom(fieldClass)) {
            return DATE_SQL_GENERATOR.get(fromUnixTime, toUnixTime);
        }

        final OffsetDateTime dateTime = OFFSET_DATE_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        return dateTime.format(formatter);
    }

    @Override
    public Object get() {
        final OffsetDateTime dateTime = OFFSET_DATE_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        return dateTime.format(formatter);
    }
}
