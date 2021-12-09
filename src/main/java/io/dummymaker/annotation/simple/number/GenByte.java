package io.dummymaker.annotation.simple.number;


import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.number.ByteGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author GoodforGod
 * @see ByteGenerator
 * @since 04.11.2018
 */
@PrimeGen(ByteGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenByte {

}
