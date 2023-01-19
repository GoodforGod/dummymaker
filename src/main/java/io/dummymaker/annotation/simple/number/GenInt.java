package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.generator.simple.number.factory.IntegerAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see io.dummymaker.generator.simple.number.IntegerGenerator
 * @since 30.05.2017
 */
@GenCustomFactory(IntegerAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenInt {

    int from() default Integer.MIN_VALUE;

    int to() default Integer.MAX_VALUE;
}
