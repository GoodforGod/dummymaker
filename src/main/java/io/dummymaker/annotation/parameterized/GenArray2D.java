package io.dummymaker.annotation.parameterized;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.parameterized.factory.Array2DAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * Generates 2 dimension array
 *
 * @author Anton Kurako (GoodforGod)
 * @see io.dummymaker.generator.parameterized.Array2DParameterizedGenerator
 * @since 04.11.2018
 */
@GenCustomFactory(Array2DAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenArray2D {

    Class<? extends Generator> value() default Generator.class;

    /**
     * @return minimum amount elements to generate for row (inclusive)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int minFirst() default 1;

    /**
     * @return maximum amount elements to generate for row (inclusive)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int maxFirst() default 3;

    /**
     * @return minimum amount elements to generate for column (inclusive)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int minSecond() default 1;

    /**
     * @return maximum amount elements to generate for column (inclusive)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int maxSecond() default 3;

    /**
     * @return fixed amount elements to generate for row (override min & max)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int fixedFirst() default -1;

    /**
     * @return fixed amount elements to generate for column (override min & max)
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    int fixedSecond() default -1;
}
