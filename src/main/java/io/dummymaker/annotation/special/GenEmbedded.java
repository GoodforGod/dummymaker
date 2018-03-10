package io.dummymaker.annotation.special;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.impl.EmbeddedGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate Inline object for that field
 *
 * @author GoodforGod
 * @since 09.03.2018
 */
@PrimeGen(EmbeddedGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenEmbedded {

}
