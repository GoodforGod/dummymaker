package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.complex.Array2DComplexGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates 2 dimension array
 *
 * @author Anton Kurako (GoodforGod)
 * @see Array2DComplexGenerator
 * @since 04.11.2018
 */
@GenCustom(Array2DComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenArray2D {

    Class<? extends Generator> value() default Generator.class;

    /**
     * @return minimum amount elements to generate for row (inclusive)
     */
    int minFirst() default 1;

    /**
     * @return maximum amount elements to generate for row (inclusive)
     */
    int maxFirst() default 2;

    /**
     * @return minimum amount elements to generate for column (inclusive)
     */
    int minSecond() default 1;

    /**
     * @return maximum amount elements to generate for column (inclusive)
     */
    int maxSecond() default 2;

    /**
     * @return fixed amount elements to generate for row (override min & max)
     */
    int fixedFirst() default -1;

    /**
     * @return fixed amount elements to generate for column (override min & max)
     */
    int fixedSecond() default -1;

    /**
     * @see GenAuto#depth()
     * @return allowed depth for inner elements to apply
     */
    int depth() default 1;
}
