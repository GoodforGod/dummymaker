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

    private final InstantGenerator instantGenerator;
    private final LocalDateGenerator localDateGenerator;
    private final LocalDateTimeGenerator localDateTimeGenerator;
    private final LocalTimeGenerator localTimeGenerator;
    private final OffsetDateTimeGenerator offsetDateTimeGenerator;
    private final OffsetTimeGenerator offsetTimeGenerator;
    private final ZonedDateTimeGenerator zonedDateTimeGenerator;
    private final ZonedOffsetGenerator zonedOffsetGenerator;

    private final DayOfWeekGenerator dayOfWeekGenerator;
    private final MonthGenerator monthGenerator;
    private final MonthDayGenerator monthDayGenerator;
    private final YearGenerator yearGenerator;
    private final YearMonthGenerator yearMonthGenerator;

    private final DateGenerator dateGenerator;
    private final DateSqlGenerator dateSqlGenerator;
    private final TimeSqlGenerator timeSqlGenerator;
    private final TimestampGenerator timestampGenerator;

    private final DateTimeFormatter formatter;

    public TimeParameterizedGenerator(long fromUnixTime, long toUnixTime, String formatter) {
        instantGenerator = new InstantGenerator(fromUnixTime, toUnixTime);
        localDateGenerator = new LocalDateGenerator(fromUnixTime, toUnixTime);
        localDateTimeGenerator = new LocalDateTimeGenerator(fromUnixTime, toUnixTime);
        localTimeGenerator = new LocalTimeGenerator(fromUnixTime, toUnixTime);
        offsetDateTimeGenerator = new OffsetDateTimeGenerator(fromUnixTime, toUnixTime);
        offsetTimeGenerator = new OffsetTimeGenerator(fromUnixTime, toUnixTime);
        zonedDateTimeGenerator = new ZonedDateTimeGenerator(fromUnixTime, toUnixTime);
        zonedOffsetGenerator = new ZonedOffsetGenerator();

        dayOfWeekGenerator = new DayOfWeekGenerator();
        monthGenerator = new MonthGenerator(fromUnixTime, toUnixTime);
        monthDayGenerator = new MonthDayGenerator(fromUnixTime, toUnixTime);
        yearGenerator = new YearGenerator(fromUnixTime, toUnixTime);
        yearMonthGenerator = new YearMonthGenerator(fromUnixTime, toUnixTime);

        dateGenerator = new DateGenerator(fromUnixTime, toUnixTime);
        dateSqlGenerator = new DateSqlGenerator(fromUnixTime, toUnixTime);
        timeSqlGenerator = new TimeSqlGenerator(fromUnixTime, toUnixTime);
        timestampGenerator = new TimestampGenerator(fromUnixTime, toUnixTime);
        this.formatter = DateTimeFormatter.ofPattern(formatter);
    }

    @Override
    public Object get(@NotNull GenType fieldType, @NotNull GenTypeBuilder typeBuilder) {
        final Class<?> fieldClass = fieldType.raw();
        if (ZoneOffset.class.isAssignableFrom(fieldClass)) {
            return zonedOffsetGenerator.get();
        } else if (DayOfWeek.class.isAssignableFrom(fieldClass)) {
            return dayOfWeekGenerator.get();
        } else if (Month.class.isAssignableFrom(fieldClass)) {
            return monthGenerator.get();
        } else if (MonthDay.class.isAssignableFrom(fieldClass)) {
            return monthDayGenerator.get();
        } else if (Year.class.isAssignableFrom(fieldClass)) {
            return yearGenerator.get();
        } else if (YearMonth.class.isAssignableFrom(fieldClass)) {
            return yearMonthGenerator.get();
        } else if (Instant.class.isAssignableFrom(fieldClass)) {
            return instantGenerator.get();
        } else if (ZonedDateTime.class.isAssignableFrom(fieldClass)) {
            return zonedDateTimeGenerator.get();
        } else if (OffsetDateTime.class.isAssignableFrom(fieldClass)) {
            return offsetDateTimeGenerator.get();
        } else if (OffsetTime.class.isAssignableFrom(fieldClass)) {
            return offsetTimeGenerator.get();
        } else if (LocalDateTime.class.isAssignableFrom(fieldClass)) {
            return localDateTimeGenerator.get();
        } else if (LocalDate.class.isAssignableFrom(fieldClass)) {
            return localDateGenerator.get();
        } else if (LocalTime.class.isAssignableFrom(fieldClass)) {
            return localTimeGenerator.get();
        } else if (Timestamp.class.isAssignableFrom(fieldClass)) {
            return timestampGenerator.get();
        } else if (Date.class.isAssignableFrom(fieldClass)) {
            return dateGenerator.get();
        } else if (Time.class.isAssignableFrom(fieldClass)) {
            return timeSqlGenerator.get();
        } else if (java.sql.Date.class.isAssignableFrom(fieldClass)) {
            return dateSqlGenerator.get();
        }

        final OffsetDateTime dateTime = offsetDateTimeGenerator.get();
        return dateTime.format(formatter);
    }

    @Override
    public Object get() {
        final OffsetDateTime dateTime = offsetDateTimeGenerator.get();
        return dateTime.format(formatter);
    }
}
