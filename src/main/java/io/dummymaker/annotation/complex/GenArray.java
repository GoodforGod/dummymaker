package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.complex.ArrayComplexGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate array
 *
 * @author GoodforGod
 * @see ArrayComplexGenerator
 * @since 04.11.2018
 */
@ComplexGen(ArrayComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenArray {

    Class<? extends IGenerator> value() default IGenerator.class;

    /**
     * Minimum entities to generate
     *
     * @return min amount
     */
    int min() default 1;

    /**
     * Max entities to generate
     *
     * @return max amount
     */
    int max() default 10;

    /**
     * Fixed number entities to generate
     * Turned off by default
     *
     * @return fixed amount
     */
    int fixed() default -1;

    /**
     * @return desired embedded depth
     * @see io.dummymaker.annotation.special.GenEmbedded#MAX
     */
    int depth() default 1;
}
