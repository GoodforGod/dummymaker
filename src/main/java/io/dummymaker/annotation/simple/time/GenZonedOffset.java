package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.time.InstantGenerator;
import io.dummymaker.generator.simple.time.ZonedOffsetGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see ZonedOffsetGenerator
 * @since 12.11.2022
 */
@PrimeGen(ZonedOffsetGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenZonedOffset {

}
