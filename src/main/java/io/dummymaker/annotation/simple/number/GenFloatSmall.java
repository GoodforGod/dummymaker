package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.number.FloatSmallGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see FloatSmallGenerator
 * @since 04.11.2018
 */
@GenCustom(FloatSmallGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenFloatSmall {

}
