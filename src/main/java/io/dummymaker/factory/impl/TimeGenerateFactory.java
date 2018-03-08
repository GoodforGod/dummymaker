package io.dummymaker.factory.impl;

import io.dummymaker.annotation.time.GenTime;
import io.dummymaker.generator.impl.time.ITimeGenerator;
import io.dummymaker.generator.impl.time.impl.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Generate time object for GenTime annotation
 *
 * @see GenTime
 * @see io.dummymaker.factory.IGenerateFactory
 * @see BasicGenerateFactory
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public class TimeGenerateFactory extends BasicGenerateFactory<ITimeGenerator<?>>{

    public TimeGenerateFactory() {
        super(GenTime.class);
    }

    /**
     * Method to produce fields value for GenTime annotation
     *
     * @see GenTime
     *
     * @param field field to populate
     * @param annotation fields annotations
     */
    @Override
    public Object generate(final Field field,
                           final Annotation annotation) {
        try {
            if(field == null || annotation == null)
                return null;

            final long from = ((GenTime) annotation).from();
            final long to = ((GenTime) annotation).to();

            if (field.getType().equals(LocalDateTime.class) || field.getType().equals(Object.class)) {
                return new LocalDateTimeGenerator().generate(from, to);
            } else if (field.getType().equals(LocalDate.class)) {
                return new LocalDateGenerator().generate(from, to);
            } else if (field.getType().equals(LocalTime.class)) {
                return new LocalTimeGenerator().generate(from, to);
            } else if (field.getType().equals(Timestamp.class)) {
                return new TimestampGenerator().generate(from, to);
            } else if (field.getType().equals(Date.class)) {
                return new DateGenerator().generate(from, to);
            } else if (field.getType().equals(String.class)) {
                return String.valueOf(new LocalDateTimeGenerator().generate(from, to));
            }
            return null;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
    }

    /**
     * Method to produce fields value for GenTime annotation
     *
     * @see GenTime
     *
     * @param field field to populate
     * @param annotation fields annotations
     * @param generator is USELESS in this generate factory due to complex gen logic
     */
    @Override
    public Object generate(final Field field,
                           final Annotation annotation,
                           final ITimeGenerator<?> generator) {
        return generate(field, annotation);
    }
}
