package io.dummymaker.annotation.special;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.SequenceGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates numeric sequence from given number (default 0) to all produced/populated Dummies Works
 * only when populate/produce list of dummies, not a single dummy
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
@PrimeGen(SequenceGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenSequence {

    long from() default 0L;
}
