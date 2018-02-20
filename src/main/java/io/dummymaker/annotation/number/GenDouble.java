package io.dummymaker.annotation.number;

import io.dummymaker.annotation.util.PrimeGenAnnotation;
import io.dummymaker.generator.impl.number.DoubleGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see DoubleGenerator
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
@PrimeGenAnnotation(DoubleGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDouble {

}
