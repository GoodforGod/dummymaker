package io.dummymaker.annotation.core;

import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.scan.IAnnotationScanner;

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
 * @author GoodforGod
 * @see PrimeGen
 * @see IGenerator
 * @see io.dummymaker.scan.IScanner
 * @see IAnnotationScanner
 * @see io.dummymaker.factory.IPopulateFactory
 * @see io.dummymaker.factory.IProduceFactory
 * @since 21.04.2018
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
public @interface ComplexGen {

    /**
     * Contains complex generator class to be called to generate values on populate factory
     *
     * @return generator
     * @see io.dummymaker.factory.IPopulateFactory
     */
    Class<? extends IComplexGenerator> value() default NullGenerator.class;
}
