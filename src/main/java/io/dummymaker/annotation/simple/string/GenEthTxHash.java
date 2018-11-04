package io.dummymaker.annotation.simple.string;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.array.CharArrayGenerator;
import io.dummymaker.generator.simple.impl.string.EthTxHashGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see CharArrayGenerator
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
@PrimeGen(EthTxHashGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenEthTxHash {

}
