package io.dummymaker.annotation.collection;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.impl.MapGenerator;
import io.dummymaker.generator.impl.string.IdBigGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate map with KEY and VALUE from generators
 *
 * @see io.dummymaker.generator.impl.collection.IMapGenerator
 * @see MapGenerator
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
@PrimeGen(MapGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenMap {

    Class<? extends IGenerator> key() default IdGenerator.class;

    Class<? extends IGenerator> value() default IdBigGenerator.class;

    int min() default 1;

    int max() default 10;

    int fixed() default 0;
}
