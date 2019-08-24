package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.generator.complex.impl.SetComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate set collection
 *
 * @author GoodforGod
 * @see SetComplexGenerator
 * @since 06.03.2018
 */
@ComplexGen(SetComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenSet {

    Class<? extends IGenerator> value() default IGenerator.class;

    /**
     * Minimum entities to generate
     * @return min amount
     */
    int min() default 1;

    /**
     * Max entities to generate
     * @return max amount
     */
    int max() default 10;

    /**
     * Fixed number entities to generate
     * Turned off by default
     * @return fixed amount
     */
    int fixed() default -1;

    /**
     * @return desired embedded depth
     * @see io.dummymaker.annotation.special.GenEmbedded#MAX
     */
    int depth() default 1;
}
