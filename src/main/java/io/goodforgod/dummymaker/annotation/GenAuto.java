package io.goodforgod.dummymaker.annotation;

import io.goodforgod.dummymaker.generator.simple.EmbeddedGenerator;
import java.lang.annotation.*;

/**
 * Annotation is used on classes and uses default suitable generators to generate random field
 * values
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.04.2018
 */
@Documented
@GenCustom(EmbeddedGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.FIELD })
public @interface GenAuto {}
