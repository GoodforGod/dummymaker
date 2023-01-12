package io.dummymaker.generator.complex;

import static io.dummymaker.util.CastUtils.castObject;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.factory.old.GenStorage;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.TimeGenerator;
import io.dummymaker.generator.simple.time.*;
import io.dummymaker.util.CastUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generate time object for GenTime annotation
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenTime
 * @see ComplexGenerator
 * @since 21.04.2018
 */
public class TimeComplexGenerator implements ComplexGenerator {

    @Override
    public @Nullable Object generate(final @NotNull Class<?> parent,
                                     final @NotNull Field field,
                                     final @NotNull GenStorage storage,
                                     final Annotation annotation,
                                     final int depth) {
        final long minUnix = (annotation == null)
                ? GenTime.MIN_UNIX
                : getMin(((GenTime) annotation));

        final long maxUnix = (annotation == null)
                ? GenTime.MAX_UNIX
                : getMax(((GenTime) annotation));

        final Class<?> fieldClass = field.getType();

        if (fieldClass.equals(Object.class) || fieldClass.equals(String.class)) {
            final DateTimeFormatter formatter = (annotation != null
                    && !GenTime.DEFAULT_FORMAT.equals(((GenTime) annotation).formatter()))
                            ? DateTimeFormatter.ofPattern(((GenTime) annotation).formatter())
                            : DateTimeFormatter.ISO_DATE_TIME;

            final LocalDateTime dateTime = (LocalDateTime) genTime(storage, LocalDateTimeGenerator.class, minUnix, maxUnix);
            try {
                final String formatted = dateTime.format(formatter);
                return castObject(formatted, fieldClass);
            } catch (Exception e) {
                return castObject(dateTime, fieldClass);
            }
        } else if (fieldClass.isAssignableFrom(MonthDay.class)) {
            return castObject(genTime(storage, MonthDayGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(Month.class)) {
            return castObject(genTime(storage, MonthGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(YearMonth.class)) {
            return castObject(genTime(storage, YearMonthGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(Year.class)) {
            return castObject(genTime(storage, YearGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(InstantGenerator.class)) {
            return castObject(genTime(storage, InstantGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(ZonedDateTime.class)) {
            return castObject(genTime(storage, ZonedDateTimeGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(OffsetDateTime.class)) {
            return castObject(genTime(storage, OffsetDateTimeGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(OffsetTime.class)) {
            return castObject(genTime(storage, OffsetTimeGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(LocalDateTime.class)) {
            return castObject(genTime(storage, LocalDateTimeGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(LocalDate.class)) {
            return castObject(genTime(storage, LocalDateGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(LocalTime.class)) {
            return castObject(genTime(storage, LocalTimeGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(Date.class)) {
            return castObject(genTime(storage, DateGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(Timestamp.class)) {
            return castObject(genTime(storage, TimestampGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(Time.class)) {
            return castObject(genTime(storage, TimeSqlGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(java.sql.Date.class)) {
            return castObject(genTime(storage, DateSqlGenerator.class, minUnix, maxUnix), fieldClass);
        }

        return null;
    }

    private @NotNull Object genTime(GenStorage storage, Class<? extends TimeGenerator> gClass, long from, long to) {
        final Generator<?> generator = (storage == null)
                ? CastUtils.instantiate(gClass)
                : storage.getGenerator(gClass);

        return ((TimeGenerator) generator).get(from, to);
    }

    private long getMin(GenTime annotation) {
        return annotation.from();
    }

    private long getMax(GenTime annotation) {
        return annotation.to();
    }

    @Override
    public Object get() {
        return new LocalDateTimeGenerator().get();
    }
}
