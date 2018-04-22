package io.dummymaker.annotation.simple;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.BooleanGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see BooleanGenerator
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
@PrimeGen(BooleanGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenBoolean {

}
