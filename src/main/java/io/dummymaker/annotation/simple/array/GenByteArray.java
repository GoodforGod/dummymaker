package io.dummymaker.annotation.simple.array;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.array.ByteArrayGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see ByteArrayGenerator
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
@PrimeGen(ByteArrayGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenByteArray {

}
