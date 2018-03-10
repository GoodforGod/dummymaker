package io.dummymaker.annotation.string;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.impl.string.JsonGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see JsonGenerator
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
@PrimeGen(JsonGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenJson {

}
