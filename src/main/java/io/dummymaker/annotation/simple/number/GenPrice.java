package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.number.PriceGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see PriceGenerator
 * @since 26.08.2022
 */
@GenCustom(PriceGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenPrice {

}
