package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.generator.complex.impl.EnumComplexGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates random enum values
 *
 * @see EnumComplexGenerator
 *
 * @author GoodforGod
 * @since 01.03.2019
 */
@ComplexGen(EnumComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenEnum {
    int MAX = 10000000;

    int from() default 0;

    int to() default MAX;
}
