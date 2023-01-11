package io.dummymaker.annotation.simple;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.UriGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see UriGenerator
 * @since 4.5.2020
 */
@GenCustom(UriGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenUri {}
