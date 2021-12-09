package io.dummymaker.annotation.simple.number;


import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.generator.complex.LongComplexGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author GoodforGod
 * @see LongComplexGenerator
 * @since 30.05.2017
 */
@ComplexGen(LongComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenLong {

    long from() default Long.MIN_VALUE;

    long to() default Long.MAX_VALUE;
}
