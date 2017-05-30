package io.generator.annotations;

import io.generator.produce.IGenerator;
import io.generator.produce.StringGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 28.05.2017
 */
@Target(value = ElementType.ANNOTATION_TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PrimeGenAnnotation {
    Class<? extends IGenerator> value() default StringGenerator.class;
}
