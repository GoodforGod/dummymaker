package io.model.dummymaker.annotation;

import io.model.dummymaker.annotation.prime.PrimeGenAnnotation;
import io.model.dummymaker.generate.LongGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
@PrimeGenAnnotation(LongGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenLong {

}
