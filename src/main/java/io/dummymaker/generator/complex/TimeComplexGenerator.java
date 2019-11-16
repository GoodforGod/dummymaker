package io.dummymaker.generator.complex;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.ITimeGenerator;
import io.dummymaker.generator.simple.time.*;
import io.dummymaker.util.CastUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static io.dummymaker.util.CastUtils.castObject;

/**
 * Generate time object for GenTime annotation
 *
 * @author GoodforGod
 * @see GenTime
 * @see IComplexGenerator
 * @since 21.04.2018
 */
public class TimeComplexGenerator implements IComplexGenerator {

    @Override
    public Object generate(final Class<?> parent,
                           final Field field,
                           final IGenStorage storage,
                           final Annotation annotation,
                           final int depth) {
        final long from = (annotation == null) ? 0 : ((GenTime) annotation).from();
        final long to = (annotation == null) ? GenTime.MAX : ((GenTime) annotation).to();

        final Class<?> fieldClass = field.getType();

        if (fieldClass.isAssignableFrom(LocalDateTime.class) || fieldClass.equals(Object.class)
                || fieldClass.equals(String.class)) {
            return castObject(genTime(storage, LocalDateTimeGenerator.class, from, to), fieldClass);
        } else if (fieldClass.isAssignableFrom(LocalDate.class)) {
            return castObject(genTime(storage, LocalDateGenerator.class, from, to), fieldClass);
        } else if (fieldClass.isAssignableFrom(LocalTime.class)) {
            return castObject(genTime(storage, LocalTimeGenerator.class, from, to), fieldClass);
        } else if (fieldClass.isAssignableFrom(Date.class)) {
            return castObject(genTime(storage, DateGenerator.class, from, to), fieldClass);
        } else if (fieldClass.isAssignableFrom(Timestamp.class)) {
            return castObject(genTime(storage, TimestampGenerator.class, from, to), fieldClass);
        } else if (fieldClass.isAssignableFrom(Time.class)) {
            return castObject(genTime(storage, TimeGenerator.class, from, to), fieldClass);
        } else if (fieldClass.isAssignableFrom(java.sql.Date.class)) {
            return castObject(genTime(storage, DateSqlGenerator.class, from, to), fieldClass);
        }
        return null;
    }

    private Object genTime(IGenStorage storage, Class<? extends ITimeGenerator> gClass, long from, long to) {
        final IGenerator generator = (storage == null)
                ? CastUtils.instantiate(gClass)
                : storage.getGenerator(gClass);

        return ((ITimeGenerator) generator).generate(from, to);
    }

    @Override
    public Object generate() {
        return new LocalDateTimeGenerator().generate();
    }
}
