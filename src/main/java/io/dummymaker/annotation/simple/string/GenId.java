package io.dummymaker.annotation.simple.string;


import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.string.IdBigGenerator;
import io.dummymaker.generator.simple.string.IdGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author GoodforGod
 * @see IdBigGenerator
 * @since 30.05.2017
 */
@PrimeGen(IdGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenId {

}
