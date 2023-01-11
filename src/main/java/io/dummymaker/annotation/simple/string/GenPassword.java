package io.dummymaker.annotation.simple.string;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.string.PasswordGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see PasswordGenerator
 * @since 12.11.2022
 */
@GenCustom(PasswordGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenPassword {

}
