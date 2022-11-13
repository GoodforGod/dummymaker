package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.complex.SetComplexGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate set collection
 *
 * @author Anton Kurako (GoodforGod)
 * @see SetComplexGenerator
 * @since 06.03.2018
 */
@GenCustom(SetComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenSet {

    Class<? extends Generator> value() default Generator.class;

    /**
     * @return minimum amount elements to generate (inclusive)
     */
    int min() default 1;

    /**
     * @return maximum amount elements to generate (inclusive)
     */
    int max() default 2;

    /**
     * @return fixed amount elements to generate (override min & max)
     */
    int fixed() default -1;

    /**
     * @see GenAuto#depth()
     * @return allowed depth for inner elements to apply
     */
    int depth() default 1;
}
