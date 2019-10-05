package io.dummymaker.annotation.special;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.EmbeddedGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate object with fields type and populate its fields marked by gen annotations
 *
 * @author GoodforGod
 * @see io.dummymaker.factory.IPopulateFactory
 * @since 09.03.2018
 */
@PrimeGen(EmbeddedGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenEmbedded {

    /**
     * Maximum depth available
     * Means that object can not be nested more than 18 times from origin one
     */
    int MAX = 20;

    /**
     * If annotation is present, than it will be 1 level depth used by factory
     * And MAX level is limit depth embedded object can have
     * <p>
     * Means that object can not be nested more than 18 times from origin one
     * Depth equals nested level from starting origin object
     *
     * @return desired embedded depth
     */
    int depth() default 1;
}
