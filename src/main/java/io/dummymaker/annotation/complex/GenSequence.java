package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.generator.parameterized.factory.SequenceAnnotationGeneratorFactory;
import java.lang.annotation.*;
import org.jetbrains.annotations.Range;

/**
 * Generates numeric sequence from given number (default 0) to all produced/populated Dummies Works
 * only when populate/produce list of dummies, not a single dummy
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
@GenCustomFactory(SequenceAnnotationGeneratorFactory.class)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenSequence {

    @Range(from = 0, to = Long.MAX_VALUE)
    long from() default 0L;
}
