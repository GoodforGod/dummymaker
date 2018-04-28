package io.dummymaker.annotation.special;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate object with fields type and populate its fields marked by gen annotations
 *
 * @see io.dummymaker.factory.IPopulateFactory
 *
 * @author GoodforGod
 * @since 09.03.2018
 */
@PrimeGen(EmbeddedGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenEmbedded {
    int MAX = 11;

    /**
     * If annotation is present, than it will be minimum 1 depth used by factory
     * And MAX level is limit embedded object depth in hierarchy
     */
    int depth() default 1;
}
