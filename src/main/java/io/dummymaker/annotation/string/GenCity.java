package io.dummymaker.annotation.string;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.impl.string.CityGenerator;

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
@PrimeGen(CityGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenCity {

}
