package io.dummymaker.annotation.collection;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.generator.impl.collection.SetStringGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see SetStringGenerator
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
@PrimeGenAnnotation(SetStringGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenSetStrings {

}
