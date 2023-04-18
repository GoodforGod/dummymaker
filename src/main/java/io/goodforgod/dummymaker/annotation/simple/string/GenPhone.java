package io.goodforgod.dummymaker.annotation.simple.string;

import io.goodforgod.dummymaker.annotation.GenCustomFactory;
import io.goodforgod.dummymaker.generator.simple.string.PhoneGenerator;
import io.goodforgod.dummymaker.generator.simple.string.factory.PhoneAnnotationGeneratorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see PhoneGenerator
 * @since 31.05.2017
 */
@GenCustomFactory(PhoneAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenPhone {

    /**
     * @return if true return phone number in format: +79876543210
     */
    boolean formatted() default false;
}
