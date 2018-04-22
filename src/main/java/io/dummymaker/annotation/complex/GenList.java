package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.generator.complex.impl.ListComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.simple.impl.collection.impl.ListGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate list collection
 *
 * @see ICollectionGenerator
 * @see ListGenerator
 *
 * @author GoodforGod
 * @since 05.03.2018
 */
@ComplexGen(ListComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenList {

    Class<? extends IGenerator> value() default IdGenerator.class;

    int min() default 1;

    int max() default 10;

    int fixed() default 0;
}
