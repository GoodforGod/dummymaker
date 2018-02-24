package io.dummymaker.annotation.collection;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.generator.impl.collection.MapIntStringGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see MapIntStringGenerator
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
@PrimeGenAnnotation(MapIntStringGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenMapIntString {

}
