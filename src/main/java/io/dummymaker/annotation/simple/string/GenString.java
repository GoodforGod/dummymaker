package io.dummymaker.annotation.simple.string;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.generator.simple.string.StringGenerator;
import io.dummymaker.generator.simple.string.factory.StringAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * @author Anton Kurako (GoodforGod)
 * @see StringGenerator
 * @since 21.02.2018
 */
@GenCustomFactory(StringAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenString {

    @Range(from = 1, to = Integer.MAX_VALUE)
    int min() default 6;

    @Range(from = 1, to = Integer.MAX_VALUE)
    int max() default 12;
}
