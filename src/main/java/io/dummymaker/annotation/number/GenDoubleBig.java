package io.dummymaker.annotation.number;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 06.03.2018
 */

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.impl.number.LongGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see LongGenerator
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
@PrimeGen(LongGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDoubleBig {

}
