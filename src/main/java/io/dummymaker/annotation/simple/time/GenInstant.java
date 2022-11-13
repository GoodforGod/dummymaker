package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.time.InstantGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see InstantGenerator
 * @since 12.11.2022
 */
@GenCustom(InstantGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenInstant {

}
