package io.goodforgod.dummymaker.annotation.simple.string;

import io.goodforgod.dummymaker.annotation.GenCustom;
import io.goodforgod.dummymaker.generator.simple.string.DocumentGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see DocumentGenerator
 * @since 31.05.2017
 */
@GenCustom(DocumentGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenPass {

}
