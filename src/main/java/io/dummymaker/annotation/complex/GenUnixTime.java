package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.generator.complex.TimeComplexGenerator;
import io.dummymaker.generator.parameterized.factory.UnixTimeParameterizedGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * @author Anton Kurako (GoodforGod)
 * @see io.dummymaker.generator.parameterized.UnixTimeParameterizedGenerator
 * @since 02.12.2022
 */
@GenCustomFactory(UnixTimeParameterizedGeneratorFactory.class)
@GenCustom(TimeComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenUnixTime {

    /**
     * Unix Timestamp for 1-1-1970.
     */
    long MIN_UNIX = 0L;

    /**
     * Unix Timestamp for 12-31-2099.
     */
    long MAX_UNIX = 4102358400L;

    /**
     * Minimum generated time from 01-01-1970 as unix timestamp.
     *
     * @return min datetime where to start generate timestamps.
     */
    @Range(from = MIN_UNIX, to = MAX_UNIX)
    long from() default MIN_UNIX;

    /**
     * Maximum generated time as unix timestamp.
     *
     * @return max datetime where to stop generate timestamps.
     */
    @Range(from = MIN_UNIX, to = MAX_UNIX)
    long to() default MAX_UNIX;
}
