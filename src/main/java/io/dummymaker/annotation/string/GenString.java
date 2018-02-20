package io.dummymaker.annotation.string;


import io.dummymaker.annotation.util.PrimeGenAnnotation;
import io.dummymaker.generator.impl.string.BigIdGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see BigIdGenerator
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
@PrimeGenAnnotation(BigIdGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenString {

}
