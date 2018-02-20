package io.dummymaker.annotation.string;


import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.generator.impl.string.BigIdGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see BigIdGenerator
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
@PrimeGenAnnotation(IdGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenId {

}
