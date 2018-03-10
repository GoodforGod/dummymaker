package io.dummymaker.annotation.time;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.impl.time.impl.LocalDateTimeGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see LocalDateTimeGenerator
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
@PrimeGen(LocalDateTimeGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenLocalDateTime {

}
