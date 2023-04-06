package io.goodforgod.dummymaker.annotation.simple.time;

import io.goodforgod.dummymaker.annotation.GenCustomFactory;
import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.simple.time.LocalDateTimeGenerator;
import io.goodforgod.dummymaker.generator.simple.time.factory.LocalDateTimeAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * @author Anton Kurako (GoodforGod)
 * @see LocalDateTimeGenerator
 * @since 31.05.2017
 */
@GenCustomFactory(LocalDateTimeAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenLocalDateTime {

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
