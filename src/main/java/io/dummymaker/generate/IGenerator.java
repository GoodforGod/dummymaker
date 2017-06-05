package io.dummymaker.generate;

/**
 * Generator used by PrimeGenAnnotation to populate field
 *
 * @see io.dummymaker.annotation.prime.PrimeGenAnnotation
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public interface IGenerator<T> {
    T generate();
}
