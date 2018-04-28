package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.number.DoubleBigGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see DoubleBigGenerator
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
@PrimeGen(DoubleBigGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDoubleBig {

}
