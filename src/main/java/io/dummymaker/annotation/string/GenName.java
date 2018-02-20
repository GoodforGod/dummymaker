package io.dummymaker.annotation.string;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.generator.impl.string.NameGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see NameGenerator
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
@PrimeGenAnnotation(NameGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenName {

}
