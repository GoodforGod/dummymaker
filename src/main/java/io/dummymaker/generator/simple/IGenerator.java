package io.dummymaker.generator.simple;

import io.dummymaker.annotation.core.PrimeGen;

import java.util.regex.Pattern;

/**
 * Generator used by PrimeGen to populate field
 *
 * @author GoodforGod
 * @see io.dummymaker.factory.IPopulateFactory
 * @see PrimeGen
 * @since 26.05.2017
 */
public interface IGenerator<T> {

    T generate();

    default Pattern getPattern() {
        return null;
    }
}
