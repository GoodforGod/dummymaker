package io.dummymaker.annotation.number;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.generator.impl.number.BigDoubleGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see BigDoubleGenerator
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
@PrimeGenAnnotation(BigDoubleGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenBigDouble {

}
