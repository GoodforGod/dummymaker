package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.impl.time.ITimeGenerator;
import io.dummymaker.generator.simple.impl.time.impl.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static io.dummymaker.util.BasicCastUtils.castObject;

/**
 * Generate time object for GenTime annotation
 *
 * @see GenTime
 *
 * @see IComplexGenerator
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public class TimeComplexGenerator implements IComplexGenerator {

    // Lazy initialisation
    private ITimeGenerator<LocalDateTime> localDateTimeGenerator;
    private ITimeGenerator<LocalDate> localDateGenerator;
    private ITimeGenerator<LocalTime> localTimeGenerator;
    private ITimeGenerator<Timestamp> timestampGenerator;
    private ITimeGenerator<Date> dateGenerator;

    @Override
    public Object generate(final Annotation annotation,
                           final Field field) {
        if (field == null || annotation == null)
            return null;

        final long from = ((GenTime) annotation).from();
        final long to = ((GenTime) annotation).to();
        final Class<?> fieldClass = field.getType();

        if (fieldClass.isAssignableFrom(LocalDateTime.class) || fieldClass.equals(Object.class) || fieldClass.equals(String.class)) {
            return castObject(getLocalDateTimeGenerator().generate(from, to), fieldClass);
        } else if (fieldClass.isAssignableFrom(LocalDate.class)) {
            return castObject(getLocalDateGenerator().generate(from, to), fieldClass);
        } else if (fieldClass.isAssignableFrom(LocalTime.class)) {
            return castObject(getLocalTimeGenerator().generate(from, to), fieldClass);
        } else if (fieldClass.isAssignableFrom(Date.class)) {
            return castObject(getDateGenerator().generate(from, to), fieldClass);
        } else if (fieldClass.isAssignableFrom(Timestamp.class)) {
            return castObject(getTimestampGenerator().generate(from, to), fieldClass);
        }
        return null;
    }

    @Override
    public Object generate() {
        return getLocalDateTimeGenerator().generate();
    }

    private ITimeGenerator<LocalDateTime> getLocalDateTimeGenerator() {
        if(localDateTimeGenerator == null)
            this.localDateTimeGenerator = new LocalDateTimeGenerator();
        return localDateTimeGenerator;
    }

    private ITimeGenerator<LocalDate> getLocalDateGenerator() {
        if(localDateGenerator == null)
            this.localDateGenerator = new LocalDateGenerator();
        return localDateGenerator;
    }

    private ITimeGenerator<LocalTime> getLocalTimeGenerator() {
        if(localTimeGenerator == null)
            this.localTimeGenerator = new LocalTimeGenerator();
        return localTimeGenerator;
    }

    private ITimeGenerator<Timestamp> getTimestampGenerator() {
        if(timestampGenerator == null)
            this.timestampGenerator = new TimestampGenerator();
        return timestampGenerator;
    }

    private ITimeGenerator<Date> getDateGenerator() {
        if(dateGenerator == null)
            this.dateGenerator = new DateGenerator();
        return dateGenerator;
    }

}
