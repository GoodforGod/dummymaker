package io.dummymaker.annotation;

import io.dummymaker.annotation.util.PrimeGenAnnotation;
import io.dummymaker.generator.NickGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see NickGenerator
 *
 * @author GoodforGod (Anton Kurako)
 * @since 06.06.2017
 */
@PrimeGenAnnotation(NickGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenNick {

}
