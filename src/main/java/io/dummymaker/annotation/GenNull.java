package io.dummymaker.annotation;

import io.dummymaker.generator.impl.NullGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see NullGenerator
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
@PrimeGen
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenNull {

}
