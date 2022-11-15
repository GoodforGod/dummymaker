package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.complex.LongComplexGenerator;
import io.dummymaker.generator.simple.number.PostalGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see PostalGenerator
 * @since 15.11.2022
 */
@GenCustom(PostalGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenPostal {

    long from() default Long.MIN_VALUE;

    long to() default Long.MAX_VALUE;
}
