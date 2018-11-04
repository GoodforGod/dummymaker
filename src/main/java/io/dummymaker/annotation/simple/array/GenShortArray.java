package io.dummymaker.annotation.simple.array;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.array.CharArrayGenerator;
import io.dummymaker.generator.simple.impl.array.ShortArrayGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see CharArrayGenerator
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
@PrimeGen(ShortArrayGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenShortArray {

}
