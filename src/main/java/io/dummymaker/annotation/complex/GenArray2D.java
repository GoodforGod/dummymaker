package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.generator.complex.impl.Array2DComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates 2 dimension array
 *
 * @see Array2DComplexGenerator
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
@ComplexGen(Array2DComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenArray2D {

    Class<? extends IGenerator> value() default IGenerator.class;

    int minFirst() default 1;
    int maxFirst() default 10;

    int minSecond() default 1;
    int maxSecond() default 10;

    int fixedFirst() default -1;
    int fixedSecond() default -1;

    /**
     * @see io.dummymaker.annotation.special.GenEmbedded#MAX
     */
    int depth() default 1;
}
