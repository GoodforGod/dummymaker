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
     * Epoch Unix Time Stamp for 1/1/3000
     */
    long MAX = 32503680000L;

    /**
     * Minimum generated time from 01.01.1970 in long UTC format
     * @return min time to gen
     */
    long from() default 0L;

    /**
     * Maximum generated time in long UTC format
     * @return max gen time
     */
    long to() default MAX;
}
