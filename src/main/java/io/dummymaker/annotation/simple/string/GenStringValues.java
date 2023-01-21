package io.dummymaker.annotation.simple.string;

import io.dummymaker.annotation.GenCustomFactory;
import io.dummymaker.generator.simple.string.StringValuesGenerator;
import io.dummymaker.generator.simple.string.factory.StringValuesAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see StringValuesGenerator
 * @since 21.01.2023
 */
@GenCustomFactory(StringValuesAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenStringValues {

    String[] value();
}
