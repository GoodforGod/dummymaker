package io.dummymaker.annotation.simple.string;


import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.string.CadastralGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author GoodforGod
 * @see CadastralGenerator
 * @since 12.10.2019
 */
@PrimeGen(CadastralGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenCadastral {

}
