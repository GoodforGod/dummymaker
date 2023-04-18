package io.goodforgod.dummymaker.annotation.simple.number;

import io.goodforgod.dummymaker.annotation.GenCustomFactory;
import io.goodforgod.dummymaker.generator.simple.number.LongGenerator;
import io.goodforgod.dummymaker.generator.simple.number.factory.LongAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * @author Anton Kurako (GoodforGod)
 * @see LongGenerator
 * @since 30.05.2017
 */
@GenCustomFactory(LongAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenLong {

    @Range(from = Long.MIN_VALUE, to = Integer.MAX_VALUE)
    long from() default 0;

    @Range(from = Long.MIN_VALUE, to = Integer.MAX_VALUE)
    long to() default Long.MAX_VALUE;
}
