package io.goodforgod.dummymaker.annotation.simple.number;

import io.goodforgod.dummymaker.annotation.GenCustomFactory;
import io.goodforgod.dummymaker.generator.simple.number.FloatGenerator;
import io.goodforgod.dummymaker.generator.simple.number.factory.FloatAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * @author Anton Kurako (GoodforGod)
 * @see FloatGenerator
 * @since 04.11.2018
 */
@GenCustomFactory(FloatAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenFloat {

    @Range(from = Long.MIN_VALUE, to = Long.MAX_VALUE)
    float from() default Float.MIN_VALUE;

    @Range(from = Long.MIN_VALUE, to = Long.MAX_VALUE)
    float to() default Float.MAX_VALUE;
}
