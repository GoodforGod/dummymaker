package io.goodforgod.dummymaker.annotation.simple.string;

import io.goodforgod.dummymaker.annotation.GenCustom;
import io.goodforgod.dummymaker.generator.simple.string.MccGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see MccGenerator
 * @since 07.09.2022
 */
@GenCustom(MccGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenMcc {

}
