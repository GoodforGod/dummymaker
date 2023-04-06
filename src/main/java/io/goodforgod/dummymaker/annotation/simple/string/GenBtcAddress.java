package io.goodforgod.dummymaker.annotation.simple.string;

import io.goodforgod.dummymaker.annotation.GenCustom;
import io.goodforgod.dummymaker.generator.simple.string.BtcAddressGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see BtcAddressGenerator
 * @since 04.11.2018
 */
@GenCustom(BtcAddressGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenBtcAddress {

}
