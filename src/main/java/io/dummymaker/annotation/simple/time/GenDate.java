package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.time.impl.DateGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This date is exported in long milliseconds format
 * So date is the milliseconds since January 1, 1970, 00:00:00 GMT
 *
 * @see DateGenerator
 *
 * @see java.util.Date
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
@PrimeGen(DateGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDate {

}
