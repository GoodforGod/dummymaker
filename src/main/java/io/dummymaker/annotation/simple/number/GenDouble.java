package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.generator.simple.number.DoubleGenerator;
import io.dummymaker.generator.simple.number.factory.DoubleAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * @author Anton Kurako (GoodforGod)
 * @see DoubleGenerator
 * @since 30.05.2017
 */
@GenCustomFactory(DoubleAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDouble {

    @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
    double from() default Double.MIN_VALUE;

    @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
    double to() default Double.MAX_VALUE;
}
