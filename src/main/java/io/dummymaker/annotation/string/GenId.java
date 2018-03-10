package io.dummymaker.annotation.string;


import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.impl.string.IdBigGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see IdBigGenerator
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
@PrimeGen(IdGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenId {

}
