package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.time.GenTime;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.impl.time.ITimeGenerator;
import io.dummymaker.generator.impl.time.impl.*;

import java.lang.annotation.Annotation;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Generate time object for GenTime annotation
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
                           final Class<?> fieldClass) {
        if (fieldClass == null || annotation == null)
            return null;

        final long from = ((GenTime) annotation).from();
        final long to = ((GenTime) annotation).to();

        if (fieldClass.isAssignableFrom(LocalDateTime.class) || fieldClass.equals(Object.class) || fieldClass.equals(String.class)) {
            return getLocalDateTimeGenerator().generate(from, to);
        } else if (fieldClass.isAssignableFrom(LocalDate.class)) {
            return getLocalDateGenerator().generate(from, to);
        } else if (fieldClass.isAssignableFrom(LocalTime.class)) {
            return getLocalTimeGenerator().generate(from, to);
        } else if (fieldClass.isAssignableFrom(Timestamp.class)) {
            return getTimestampGenerator().generate(from, to);
        } else if (fieldClass.isAssignableFrom(Date.class)) {
            return getDateGenerator().generate(from, to);
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
