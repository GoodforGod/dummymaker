package io.dummymaker.annotation.simple.string;


import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.string.UrlGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Anton Kurako (GoodforGod)
 * @see UrlGenerator
 * @since 4.5.2020
 */
@PrimeGen(UrlGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenUrl {
}
