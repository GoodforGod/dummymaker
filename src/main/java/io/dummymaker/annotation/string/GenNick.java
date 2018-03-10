package io.dummymaker.annotation.string;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.impl.string.NickGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see NickGenerator
 *
 * @author GoodforGod (Anton Kurako)
 * @since 06.06.2017
 */
@PrimeGen(NickGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenNick {

}
