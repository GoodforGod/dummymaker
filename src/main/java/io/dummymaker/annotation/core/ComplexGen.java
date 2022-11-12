package io.dummymaker.annotation.core;

import io.dummymaker.generator.ComplexGenerator;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.NullGenerator;
import io.dummymaker.scan.AnnotationScanner;
import io.dummymaker.scan.MapScanner;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Complex Gen annotation used to create new annotations of specific complex generator provided type
 * Used as a marker annotation for other annotations
 * <p>
 * Is used by scanners and populate/produce factories
 * <p>
 * This annotation is a also core one (as prime one) to support population factory
 *
 * @author Anton Kurako (GoodforGod)
 * @see PrimeGen
 * @see Generator
 * @see MapScanner
 * @see AnnotationScanner
 * @since 21.04.2018
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
public @interface ComplexGen {

    /**
     * Contains complex generator class to be called to generate values on populate factory
     *
     * @return generator
     */
    Class<? extends ComplexGenerator> value() default NullGenerator.class;
}
