package io.generator.annotations;

import io.generator.produce.DoubleGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
@PrimeGenAnnotation(DoubleGenerator.class)
@Target(ElementType.FIELD)
public @interface GenDouble {

}
