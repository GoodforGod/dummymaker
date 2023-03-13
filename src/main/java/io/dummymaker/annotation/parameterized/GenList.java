package io.dummymaker.annotation.parameterized;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.parameterized.factory.ListAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Range;

/**
 * Generate list collection
 *
 * @author Anton Kurako (GoodforGod)
 * @see io.dummymaker.generator.parameterized.ListParameterizedGenerator
 * @since 05.03.2018
 */
@GenCustomFactory(ListAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenList {

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
