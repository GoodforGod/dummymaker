package io.dummymaker.annotation.simple.string;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.string.NounGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see NounGenerator
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
@PrimeGen(NounGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenNoun {

}
