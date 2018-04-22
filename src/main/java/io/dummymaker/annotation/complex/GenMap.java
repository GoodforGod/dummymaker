package io.dummymaker.annotation.complex;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.generator.complex.impl.MapComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.collection.impl.MapGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate map with KEY and VALUE from generators
 *
 * @see io.dummymaker.generator.simple.impl.collection.IMapGenerator
 * @see MapGenerator
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
@ComplexGen(MapComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenMap {

    Class<? extends IGenerator> key() default IdGenerator.class;

    Class<? extends IGenerator> value() default IdGenerator.class;

    int min() default 1;

    int max() default 10;

    int fixed() default 0;
}
