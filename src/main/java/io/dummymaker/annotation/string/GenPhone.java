package io.dummymaker.annotation.string;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.impl.string.PhoneGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see PhoneGenerator
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
@PrimeGen(PhoneGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenPhone {

}
