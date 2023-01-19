package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.simple.time.DateGenerator;
import io.dummymaker.generator.simple.time.factory.DateAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * This date is exported in long milliseconds format So date is the milliseconds since January 1,
 * 1970, 00:00:00 GMT to 1/1/3000
 *
 * @author Anton Kurako (GoodforGod)
 * @see DateGenerator
 * @see java.util.Date
 * @since 21.02.2018
 */
@GenCustomFactory(DateAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDate {

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
