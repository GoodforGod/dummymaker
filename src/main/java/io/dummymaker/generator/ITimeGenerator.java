package io.dummymaker.generator;

import io.dummymaker.annotation.complex.GenTime;

/**
 * Generates time/datetime/timestamp in range if necessary
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public interface ITimeGenerator<T> extends IGenerator<T> {

    /**
     * @param from minimum 0 represents 1970.1.1
     * @param to   as a maximum of GenTime.MAX
     * @return generated time object
     * @see GenTime#MAX
     */
    T generate(long from, long to);
}
