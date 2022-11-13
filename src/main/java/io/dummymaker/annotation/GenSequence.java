package io.dummymaker.annotation;

import io.dummymaker.generator.simple.SequenceGenerator;

import java.lang.annotation.*;

/**
 * Generates numeric sequence from given number (default 0) to all produced/populated Dummies Works
 * only when populate/produce list of dummies, not a single dummy
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
@Documented
@GenCustom(SequenceGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenSequence {

    long from() default 0L;
}
