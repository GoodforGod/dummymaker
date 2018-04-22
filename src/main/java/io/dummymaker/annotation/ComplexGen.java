package io.dummymaker.annotation;

import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.scan.IAnnotationScanner;

import java.lang.annotation.*;

/**
 * Complex Gen annotation used to create new annotations of specific complex generator provided type
 * Used as a marker annotation for other annotations
 *
 * Is used by scanners and populate/produce factories
 *
 * This annotation is a also core one (as prime one) to support population factory
 *
 * @see PrimeGen
 *
 * @see IGenerator
 * @see io.dummymaker.scan.IScanner
 * @see IAnnotationScanner
 *
 * @see io.dummymaker.factory.IPopulateFactory
 * @see io.dummymaker.factory.IProduceFactory
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
public @interface ComplexGen {

    /**
     * Contains complex generator class to be called to generate values on populate factory
     *
     * @see io.dummymaker.factory.IPopulateFactory
     *
     * @return generator
     */
    Class<? extends IComplexGenerator> value() default NullGenerator.class;
}
