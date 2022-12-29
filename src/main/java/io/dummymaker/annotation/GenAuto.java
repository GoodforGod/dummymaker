package io.dummymaker.annotation;

import io.dummymaker.factory.old.GenSupplier;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import java.lang.annotation.*;

/**
 * Annotation is used on classes and uses default suitable generators to fill class fields
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenSupplier
 * @since 21.04.2018
 */
@Documented
@GenCustom(EmbeddedGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.FIELD })
public @interface GenAuto {}
