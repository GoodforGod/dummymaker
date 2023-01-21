package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.number.DoubleSmallGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see DoubleSmallGenerator
 * @since 30.05.2017
 */
@GenCustom(DoubleSmallGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDoubleSmall {

}
