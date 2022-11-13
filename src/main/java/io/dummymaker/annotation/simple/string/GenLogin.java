package io.dummymaker.annotation.simple.string;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.string.LoginGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @see LoginGenerator
 * @since 06.06.2017
 */
@GenCustom(LoginGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenLogin {

}
