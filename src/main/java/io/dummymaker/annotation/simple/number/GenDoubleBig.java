package io.dummymaker.annotation.simple.number;


import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.number.DoubleBigGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author GoodforGod
 * @see DoubleBigGenerator
 * @since 30.05.2017
 */
@PrimeGen(DoubleBigGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDoubleBig {

}
