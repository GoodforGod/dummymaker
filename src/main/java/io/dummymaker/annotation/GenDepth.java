package io.dummymaker.annotation;

import io.dummymaker.factory.GenSupplier;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import org.jetbrains.annotations.Range;

import java.lang.annotation.*;

/**
 * Annotation is used on classes and uses default suitable generators to fill class fields
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenSupplier
 * @since 21.11.2022
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.FIELD})
public @interface GenDepth {

    /**
     * Maximum depth available, means that type can't be nested more than {@link GenDepth#MAX} times from initial type
     */
    int MAX = 50;

    /**
     * Default depth
     */
    int DEFAULT = 10;

    /**
     * If annotation is present, then it will be 1 level depth used by factory And MAX level is limit
     * depth embedded object can have
     * <p>
     * Means that object can not be nested more than 18 times from origin one Depth equals nested level
     * from starting origin object
     *
     * @return desired embedded depth
     */
    @Range(from = 1, to = GenDepth.MAX)
    int depth() default DEFAULT;
}