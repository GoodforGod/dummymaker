package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.number.BigIntegerGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author GoodforGod
 * @see BigIntegerGenerator
 * @since 21.07.2019
 */
@PrimeGen(BigIntegerGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenBigInteger {

}
