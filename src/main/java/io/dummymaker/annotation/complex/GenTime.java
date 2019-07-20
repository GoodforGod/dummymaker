package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.generator.complex.impl.TimeComplexGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see TimeComplexGenerator
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
@ComplexGen(TimeComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenTime {

    /**
     * Epoch Unix Time Stamp - 1/1/3000
     */
    long MAX = 32503680000L;

    long from() default 0L;

    long to() default MAX;
}
