package io.dummymaker.annotation.simple.time;


import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.time.LocalTimeGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author GoodforGod
 * @see LocalTimeGenerator
 * @since 21.02.2018
 */
@PrimeGen(LocalTimeGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenLocalTime {

}
