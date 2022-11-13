package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.GenCustom;
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
@GenCustom(EnumComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenEnum {

    String[] only() default {};

    String[] exclude() default {};
}
