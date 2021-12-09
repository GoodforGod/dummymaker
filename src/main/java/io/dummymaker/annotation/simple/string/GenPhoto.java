package io.dummymaker.annotation.simple.string;


import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.string.PhotoGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author GoodforGod
 * @see PhotoGenerator
 * @since 04.12.2021
 */
@PrimeGen(PhotoGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenPhoto {

}
