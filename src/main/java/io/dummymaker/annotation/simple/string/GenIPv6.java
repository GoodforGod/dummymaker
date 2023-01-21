package io.dummymaker.annotation.simple.string;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.string.IPv6Generator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see IPv6Generator
 * @since 21.01.2023
 */
@GenCustom(IPv6Generator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenIPv6 {

}
