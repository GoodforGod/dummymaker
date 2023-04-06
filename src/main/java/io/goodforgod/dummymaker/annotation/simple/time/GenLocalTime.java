package io.goodforgod.dummymaker.annotation.simple.time;

import io.goodforgod.dummymaker.annotation.GenCustomFactory;
import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.simple.time.LocalTimeGenerator;
import io.goodforgod.dummymaker.generator.simple.time.factory.LocalTimeAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * @author Anton Kurako (GoodforGod)
 * @see LocalTimeGenerator
 * @since 21.02.2018
 */
@GenCustomFactory(LocalTimeAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenLocalTime {

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
