package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.simple.time.TimeSqlGenerator;
import io.dummymaker.generator.simple.time.factory.TimeSqlAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * @author Anton Kurako (GoodforGod)
 * @see TimeSqlGenerator
 * @since 10.03.2019
 */
@GenCustomFactory(TimeSqlAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenTimeSql {

    /**
     * Minimum generated time from 01-01-1970 as unix timestamp.
     *
     * @return min datetime where to start generate timestamps.
     */
    @Range(from = GenTime.MIN, to = GenTime.MAX)
    long from() default GenTime.MIN;

    /**
     * Maximum generated time as unix timestamp.
     *
     * @return max datetime where to stop generate timestamps.
     */
    @Range(from = GenTime.MIN, to = GenTime.MAX)
    long to() default GenTime.MAX;
}
