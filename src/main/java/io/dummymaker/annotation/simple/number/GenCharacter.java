package io.dummymaker.annotation.simple.number;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.number.CharacterGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see CharacterGenerator
 * @since 04.11.2018
 */
@GenCustom(CharacterGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenCharacter {

}
