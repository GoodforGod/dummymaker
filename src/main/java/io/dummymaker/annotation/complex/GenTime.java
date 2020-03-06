package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.generator.complex.TimeComplexGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author GoodforGod
 * @see TimeComplexGenerator
 * @since 06.03.2018
 */
@ComplexGen(TimeComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenTime {

    /**
     * Unix Timestamp for 12-31-2099.
     */
    long MAX_UNIX = 4102358400L;

    /**
     * Unix Timestamp for 1-1-1970.
     */
    long MIN_UNIX = 0L;

    String MIN_DATE_TIME = "1-1-1970";

    String MAX_DATE_TIME = "30-12-2099";

    /**
     * Minimum generated time from 01-01-1970 as unix timestamp.
     *
     * @return min datetime where to start generate timestamps.
     */
    long minUnix() default MIN_UNIX;

    /**
     * Maximum generated time as unix timestamp.
     *
     * @return max datetime where to stop generate timestamps.
     */
    long maxUnix() default MAX_UNIX;

    /**
     * Minimum generated time.
     * This has MORE PRIORITY than unix time.
     *
     * @return min datetime where to start generate timestamps.
     */
    String min() default MIN_DATE_TIME;

    /**
     * Maximum generated time.
     * This has MORE PRIORITY than unix time.
     *
     * @return max datetime where to stop generate timestamps.
     */

    String max() default MAX_DATE_TIME;

    /**
     * Format in which export date the it is converted to string.
     * 
     * @return export format (default is ISO 8601)
     */
    String format() default "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
}
