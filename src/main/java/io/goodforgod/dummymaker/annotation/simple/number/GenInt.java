package io.goodforgod.dummymaker.annotation.simple.number;

import io.goodforgod.dummymaker.annotation.GenCustomFactory;
import io.goodforgod.dummymaker.generator.simple.number.IntegerGenerator;
import io.goodforgod.dummymaker.generator.simple.number.factory.IntegerAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * @author Anton Kurako (GoodforGod)
 * @see IntegerGenerator
 * @since 30.05.2017
 */
@GenCustomFactory(IntegerAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenInt {

    @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
    int from() default 0;

    @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
    int to() default Integer.MAX_VALUE;
}
