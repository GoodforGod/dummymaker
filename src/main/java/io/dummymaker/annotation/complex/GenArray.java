package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.generator.complex.impl.ArrayComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate array
 *
 * @see ArrayComplexGenerator
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
@ComplexGen(ArrayComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenArray {

    Class<? extends IGenerator> value() default IGenerator.class;

    int min() default 1;

    int max() default 10;

    int fixed() default -1;

    /**
     * @see io.dummymaker.annotation.special.GenEmbedded#MAX
     * @return desired embedded depth
     */
    int depth() default 1;
}
