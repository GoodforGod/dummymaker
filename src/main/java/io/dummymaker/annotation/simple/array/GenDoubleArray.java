package io.dummymaker.annotation.simple.array;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.array.DoubleArrayGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see DoubleArrayGenerator
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
@PrimeGen(DoubleArrayGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDoubleArray {

}
