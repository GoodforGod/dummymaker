package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.time.TimeGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author GoodforGod
 * @see TimeGenerator
 * @since 10.03.2019
 */
@PrimeGen(TimeGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenTimeSql {

}
