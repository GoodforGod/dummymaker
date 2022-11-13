package io.dummymaker.annotation.simple;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.BooleanGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see BooleanGenerator
 * @since 21.02.2018
 */
@GenCustom(BooleanGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenBoolean {

}
