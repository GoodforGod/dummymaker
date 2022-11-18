package io.dummymaker.annotation;

import io.dummymaker.factory.GenSupplier;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.lang.annotation.*;

/**
 * Annotation is used on classes and uses default suitable generators to fill class fields
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenSupplier
 * @since 21.04.2018
 */
@Documented
@GenCustom(EmbeddedGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.FIELD})
public @interface GenAuto {

    /**
     * Maximum depth available Means that object can not be nested more than 18 times from origin one
     */
    int DEPTH_MAX = 50;

    /**
     * If annotation is present, then it will be 1 level depth used by factory And MAX level is limit
     * depth embedded object can have
     * <p>
     * Means that object can not be nested more than 18 times from origin one Depth equals nested level
     * from starting origin object
     *
     * @return desired embedded depth
     */
    @Range(from = 1, to = GenAuto.DEPTH_MAX)
    int depth() default 10;
}
