package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.complex.MapComplexGenerator;
import org.jetbrains.annotations.Range;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate map with KEY and VALUE from generators
 *
 * @author Anton Kurako (GoodforGod)
 * @see MapComplexGenerator
 * @since 06.03.2018
 */
@GenCustom(MapComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenMap {

    Class<? extends Generator> key() default Generator.class;

    Class<? extends Generator> value() default Generator.class;

    /**
     * @return minimum amount elements to generate (inclusive)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int min() default 1;

    /**
     * @return maximum amount elements to generate (inclusive)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int max() default 2;

    /**
     * @return fixed amount elements to generate (override min & max)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int fixed() default -1;

    /**
     * @see GenAuto#depth()
     * @return allowed depth for inner elements to apply
     */
    @Range(from = 1, to = GenAuto.DEPTH_MAX)
    int depth() default 1;
}
