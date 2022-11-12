package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.generator.complex.EnumComplexGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates random enum values
 *
 * @author Anton Kurako (GoodforGod)
 * @see EnumComplexGenerator
 * @since 01.03.2019
 */
@ComplexGen(EnumComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenEnum {

    String[] only() default {};

    String[] exclude() default {};
}
