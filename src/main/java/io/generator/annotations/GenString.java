package io.generator.annotations;


import io.generator.produce.StringGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
@PrimeGenAnnotation(StringGenerator.class)
@Target(ElementType.FIELD)
public @interface GenString {
    String value();
}
