package io.dummymaker.annotation.collection;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.impl.collection.impl.SetGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

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
@PrimeGen(SetGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenSet {

    Class<? extends IGenerator> generator() default IdGenerator.class;

    int min() default 1;

    int max() default 10;

    int fixed() default 0;
}
