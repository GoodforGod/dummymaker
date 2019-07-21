package io.dummymaker.annotation.special;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use when you have your custom generator
 * But dont want to implement your custom annotation
 * <p>
 * Works with simple and complex generators
 *
 * @author GoodforGod
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @see IGenerator
 * @see PrimeGen
 * @since 20.07.2019
 */
@PrimeGen
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenCustom {

    /**
     * Contains generator class to be called to generate values on factory
     *
     * @return generator
     */
    Class<? extends IGenerator> value() default NullGenerator.class;
}
