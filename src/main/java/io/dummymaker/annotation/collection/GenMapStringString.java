package io.dummymaker.annotation.collection;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.generator.impl.collection.MapStringStringGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see MapStringStringGenerator
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
@PrimeGenAnnotation(MapStringStringGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenMapStringString {

}
