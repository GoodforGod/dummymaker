package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.generator.simple.number.ShortGenerator;
import io.dummymaker.generator.simple.number.factory.ShortAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * @author Anton Kurako (GoodforGod)
 * @see ShortGenerator
 * @since 04.11.2018
 */
@GenCustomFactory(ShortAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenShort {

    @Range(from = Short.MIN_VALUE, to = Short.MAX_VALUE)
    short from() default Short.MIN_VALUE;

    @Range(from = Short.MIN_VALUE, to = Short.MAX_VALUE)
    short to() default Short.MAX_VALUE;
}
