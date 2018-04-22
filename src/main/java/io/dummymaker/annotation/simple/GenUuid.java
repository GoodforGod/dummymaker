package io.dummymaker.annotation.simple;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.UuidGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see UuidGenerator
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
@PrimeGen(UuidGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenUuid {

}
