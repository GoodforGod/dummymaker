package io.dummymaker.annotation.core;

import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.NullGenerator;
import io.dummymaker.scan.AnnotationScanner;
import io.dummymaker.scan.MapScanner;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Prime annotations, used to create new annotations of specific generator provided type Used as a
 * marker annotation for other annotations
 * <p>
 * Is used by scanners and populate/produce factories
 * <p>
 * This annotation is a core one to support population factory
 *
 * @author Anton Kurako (GoodforGod)
 * @see Generator
 * @see MapScanner
 * @see AnnotationScanner
 * @since 28.05.2017
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
public @interface PrimeGen {

    /**
     * Contains generator class to be called to generate values on factory
     *
     * @return generator
     */
    Class<? extends Generator> value() default NullGenerator.class;
}
