package io.dummymaker.generator.simple;

import io.dummymaker.annotation.PrimeGen;

/**
 * Generator used by PrimeGen to populate field
 *
 * @see io.dummymaker.factory.IPopulateFactory
 * @see PrimeGen
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public interface IGenerator<T> {
    T generate();
}
