package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.time.ZonedDateTimeGenerator;
import io.dummymaker.generator.simple.time.ZonedOffsetGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see ZonedDateTimeGenerator
 * @since 12.11.2022
 */
@PrimeGen(ZonedDateTimeGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenZonedDateTime {

}
