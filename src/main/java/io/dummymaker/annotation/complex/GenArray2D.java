package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.complex.Array2DComplexGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates 2 dimension array
 *
 * @author GoodforGod
 * @see Array2DComplexGenerator
 * @since 04.11.2018
 */
@ComplexGen(Array2DComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenArray2D {

    Class<? extends IGenerator> value() default IGenerator.class;

    /**
     * First dimension minimum entities to generate
     *
     * @return minimum amount for 1 dimension
     */
    int minFirst() default 1;

    /**
     * First dimension max entities to generate
     *
     * @return max amount for 1 dimension
     */
    int maxFirst() default 10;

    /**
     * Second dimension minimum entities to generate
     *
     * @return minimum amount for 2 dimension
     */
    int minSecond() default 1;

    /**
     * Second dimension max entities to generate
     *
     * @return max amount for 2 dimension
     */
    int maxSecond() default 10;

    /**
     * First dimension fixed amount entities to generate Turned off by default
     *
     * @return fixed amount
     */
    int fixedFirst() default -1;

    /**
     * Second dimension fixed amount entities to generate Turned off by default
     *
     * @return fixed amount
     */
    int fixedSecond() default -1;

    /**
     * @return desired embedded depth
     * @see io.dummymaker.annotation.special.GenEmbedded#MAX
     */
    int depth() default 1;
}
