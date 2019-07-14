package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.generator.complex.impl.MapComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate map with KEY and VALUE from generators
 *
 * @see MapComplexGenerator
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
@ComplexGen(MapComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenMap {

    Class<? extends IGenerator> key() default IGenerator.class;

    Class<? extends IGenerator> value() default IGenerator.class;

    int min() default 1;

    int max() default 10;

    int fixed() default -1;

    /**
     * @see io.dummymaker.annotation.special.GenEmbedded#MAX
     */
    int depth() default 1;
}
