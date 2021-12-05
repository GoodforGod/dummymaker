package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.complex.MapComplexGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate map with KEY and VALUE from generators
 *
 * @author GoodforGod
 * @see MapComplexGenerator
 * @since 06.03.2018
 */
@ComplexGen(MapComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenMap {

    Class<? extends IGenerator> key() default IGenerator.class;

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
    int max() default 2;

    /**
     * Fixed number entities to generate Turned off by default
     *
     * @return fixed amount
     */
    int fixed() default -1;

    /**
     * @return allowed depth level
     * @see io.dummymaker.annotation.special.GenEmbedded
     */
    int depth() default 1;
}
