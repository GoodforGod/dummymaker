package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.number.DoubleGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see DoubleGenerator
 * @since 30.05.2017
 */
@PrimeGen(DoubleGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDouble {

}
