package io.dummymaker.annotation.simple.array;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.array.IntArrayGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see IntArrayGenerator
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
@PrimeGen(IntArrayGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenIntArray {

}
