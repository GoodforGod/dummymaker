package io.dummymaker.annotation.number;

import io.dummymaker.annotation.util.PrimeGenAnnotation;
import io.dummymaker.generator.impl.number.LongGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see LongGenerator
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
@PrimeGenAnnotation(LongGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenLong {

}
