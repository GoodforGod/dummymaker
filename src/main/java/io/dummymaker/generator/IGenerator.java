package io.dummymaker.generator;

import io.dummymaker.annotation.base.PrimeGenAnnotation;

/**
 * Generator used by PrimeGenAnnotation to populate field
 *
 * @see PrimeGenAnnotation
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public interface IGenerator<T> {
    T generate();
}
