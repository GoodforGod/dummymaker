package io.goodforgod.dummymaker.annotation.simple.time;

import io.goodforgod.dummymaker.annotation.GenCustomFactory;
import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.simple.time.DurationGenerator;
import io.goodforgod.dummymaker.generator.simple.time.factory.DurationAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * Generates random Duration
 *
 * @author Anton Kurako (GoodforGod)
 * @see DurationGenerator
 * @see java.time.Duration
 * @since 27.03.2023
 */
@GenCustomFactory(DurationAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDuration {

    /**
     * Minimum generated time from 01-01-1970 as unix timestamp.
     *
     * @return min datetime where to start generate timestamps.
     */
    @Range(from = 1, to = GenTime.MAX)
    long min() default 1;

    /**
     * Maximum generated time as unix timestamp.
     *
     * @return max datetime where to stop generate timestamps. (default is 1 Year)
     */
    @Range(from = 1, to = GenTime.MAX)
    long max() default 31_557_600_000L;
}
