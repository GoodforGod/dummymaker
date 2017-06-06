package io.dummymaker.annotation;

import io.dummymaker.annotation.prime.PrimeGenAnnotation;
import io.dummymaker.generate.CityGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see CityGenerator
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
@PrimeGenAnnotation(CityGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenCity {

}
