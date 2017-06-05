package io.dummymaker.annotation;

import io.dummymaker.annotation.prime.PrimeGenAnnotation;
import io.dummymaker.generate.EmailGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
@PrimeGenAnnotation(EmailGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenEmail {

}
