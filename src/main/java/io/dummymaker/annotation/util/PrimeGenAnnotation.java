package io.dummymaker.annotation.util;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.NullGenerator;

import java.lang.annotation.*;

/**
 * Prime annotations, used to create new annotations of specific generator provided type
 *
 * @author GoodforGod
 * @since 28.05.2017
 */
@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
public @interface PrimeGenAnnotation {

    /**
     * Contains generator class to be called to generate values
     * @return generator
     */
    Class<? extends IGenerator> value() default NullGenerator.class;
}
