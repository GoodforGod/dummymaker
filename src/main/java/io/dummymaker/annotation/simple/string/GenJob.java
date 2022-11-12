package io.dummymaker.annotation.simple.string;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.generator.simple.string.JobGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see JobGenerator
 * @since 16.07.2019
 */
@PrimeGen(JobGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenJob {

}
