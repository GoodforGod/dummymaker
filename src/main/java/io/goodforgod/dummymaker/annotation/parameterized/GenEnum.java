package io.goodforgod.dummymaker.annotation.parameterized;

import io.goodforgod.dummymaker.annotation.GenCustomFactory;
import io.goodforgod.dummymaker.generator.parameterized.EnumParameterizedGenerator;
import io.goodforgod.dummymaker.generator.parameterized.factory.EnumAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates random enum values
 *
 * @author Anton Kurako (GoodforGod)
 * @see EnumParameterizedGenerator
 * @since 01.03.2019
 */
@GenCustomFactory(EnumAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenEnum {

    String[] only() default {};

    String[] exclude() default {};
}
