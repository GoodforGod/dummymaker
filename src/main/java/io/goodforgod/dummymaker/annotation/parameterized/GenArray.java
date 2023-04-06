package io.goodforgod.dummymaker.annotation.parameterized;

import io.goodforgod.dummymaker.annotation.GenCustomFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.parameterized.ArrayParameterizedGenerator;
import io.goodforgod.dummymaker.generator.parameterized.factory.ArrayAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * Generate array
 *
 * @author Anton Kurako (GoodforGod)
 * @see ArrayParameterizedGenerator
 * @since 04.11.2018
 */
@GenCustomFactory(ArrayAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenArray {

    Class<? extends Generator> value() default Generator.class;

    /**
     * @return minimum amount elements to generate (inclusive)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int min() default 1;

    /**
     * @return maximum amount elements to generate (inclusive)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int max() default 3;

    /**
     * @return fixed amount elements to generate (override min and max)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int fixed() default -1;
}
