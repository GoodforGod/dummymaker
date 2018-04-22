package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.generator.complex.impl.SetComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.simple.impl.collection.impl.SetGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate set collection
 *
 * @see ICollectionGenerator
 * @see SetGenerator
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
@ComplexGen(SetComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenSet {

    Class<? extends IGenerator> value() default IdGenerator.class;

    int min() default 1;

    int max() default 10;

    int fixed() default 0;
}
