package io.dummymaker.generator.complex;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.ITimeGenerator;
import io.dummymaker.generator.simple.time.*;
import io.dummymaker.util.CastUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static io.dummymaker.util.CastUtils.castObject;
import static io.dummymaker.util.StringUtils.isNotBlank;

/**
 * Generate time object for GenTime annotation
 *
 * @author GoodforGod
 * @see GenTime
 * @see IComplexGenerator
 * @since 21.04.2018
 */
public class TimeComplexGenerator implements IComplexGenerator {

    private static final Logger logger = LoggerFactory.getLogger(TimeComplexGenerator.class);

    @Override
    public Object generate(final Class<?> parent,
                           final Field field,
                           final IGenStorage storage,
                           final Annotation annotation,
                           final int depth) {
        final GenTime genTime = (GenTime) annotation;
        final long minUnix = getMin(genTime);
        final long maxUnix = getMax(genTime);

        final Class<?> fieldClass = field.getType();

        if (fieldClass.equals(Object.class) || fieldClass.equals(String.class)) {
            final DateTimeFormatter formatter = GenTime.EXPORT_FORMAT.equals(genTime.formatter())
                    ? DateTimeFormatter.ISO_DATE_TIME
                    : DateTimeFormatter.ofPattern(genTime.formatter());

            final LocalDateTime dateTime = (LocalDateTime) genTime(storage, LocalDateTimeGenerator.class, minUnix, maxUnix);
            try {
                final String formatted = dateTime.format(formatter);
                return castObject(formatted, fieldClass);
            } catch (Exception e) {
                return castObject(dateTime, fieldClass);
            }
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
            return castObject(genTime(storage, TimeGenerator.class, minUnix, maxUnix), fieldClass);
        } else if (fieldClass.isAssignableFrom(java.sql.Date.class)) {
            return castObject(genTime(storage, DateSqlGenerator.class, minUnix, maxUnix), fieldClass);
        }
        return null;
    }

    private Object genTime(IGenStorage storage, Class<? extends ITimeGenerator> gClass, long from, long to) {
        final IGenerator generator = (storage == null)
                ? CastUtils.instantiate(gClass)
                : storage.getGenerator(gClass);

        return ((ITimeGenerator) generator).generate(from, to);
    }

    private long getMin(GenTime annotation) {
        if (annotation == null)
            return 0;

        try {
            final String min = annotation.min();
            if (isNotBlank(min) && !GenTime.MIN_DATE_TIME.equals(min))
                return LocalDate.parse(min).toEpochDay();
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }

        return annotation.minUnix();
    }

    private long getMax(GenTime annotation) {
        if (annotation == null)
            return GenTime.MAX_UNIX;

        try {
            final String max = annotation.max();
            if (isNotBlank(max) && !GenTime.MAX_DATE_TIME.equals(max))
                return LocalDate.parse(max).toEpochDay();
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }

        return annotation.maxUnix();
    }

    @Override
    public Object generate() {
        return new LocalDateTimeGenerator().generate();
    }
}
