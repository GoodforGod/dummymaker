package io.dummymaker.annotation.collection;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.impl.collection.impl.ListGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

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
@PrimeGen(ListGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenList {

    Class<? extends IGenerator> generator() default IdGenerator.class;

    int min() default 1;

    int max() default 10;

    int fixed() default 0;
}
