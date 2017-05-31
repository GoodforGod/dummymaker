package io.generator.annotation.prime;

import io.generator.produce.IGenerator;
import io.generator.produce.NullGenerator;

import java.lang.annotation.*;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 28.05.2017
 */
@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
public @interface PrimeGenAnnotation {
    Class<? extends IGenerator> value() default NullGenerator.class;
}
