package io.dummymaker.annotation.parameterized;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.generator.parameterized.factory.TimeAnnotationGeneratorFactory;
import org.jetbrains.annotations.Range;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see io.dummymaker.generator.parameterized.TimeParameterizedGenerator
 * @since 06.03.2018
 */
@GenCustomFactory(TimeAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenTime {

    /**
     * Unix Timestamp for 1-1-1970.
     */
    long MIN = 0L;

    /**
     * Unix Timestamp for 12-31-2099.
     */
    long MAX = 4102358400L;

    /**
     * ISO 8601 datetime format used when mapping to String
     *
     * @see java.time.OffsetDateTime is used when mapping to String
     */
    String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * Minimum generated time from 01-01-1970 as unix timestamp.
     *
     * @return min datetime where to start generate timestamps.
     */
    @Range(from = MIN, to = MAX)
    long from() default MIN;

    /**
     * Maximum generated time as unix timestamp.
     *
     * @return max datetime where to stop generate timestamps.
     */
    @Range(from = MIN, to = MAX)
    long to() default MAX;

    /**
     * Format in which export date is converted to string.
     *
     * @return export format (default is ISO 8601)
     */
    String formatter() default DEFAULT_FORMAT;
}
