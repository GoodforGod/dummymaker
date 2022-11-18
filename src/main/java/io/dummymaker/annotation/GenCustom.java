package io.dummymaker.annotation;

import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.NullGenerator;
import io.dummymaker.scan.AnnotationScanner;
import io.dummymaker.scan.MapScanner;
import org.jetbrains.annotations.Range;

import java.lang.annotation.*;

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
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface GenCustom {

    /**
     * @return generator class to be called to generate values on factory (generator is expected to be initialized with Zero Argument Constructor)
     */
    Class<? extends Generator<?>> value();

    /**
     * @return allowed depth level
     * @see GenAuto#depth()
     */
    @Range(from = 1, to = GenAuto.DEPTH_MAX)
    int depth() default 10;
}
