package io.dummymaker.annotation.time;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.generator.impl.time.DateGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see DateGenerator
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
@PrimeGenAnnotation(DateGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDate {
}
