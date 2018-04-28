package io.dummymaker.generator.simple.impl.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Generates time/datetime/timestamp in range if necessary
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public interface ITimeGenerator<T> extends IGenerator<T> {

    /**
     * @param from minimum 0 represents 1970.1.1
     * @param to as a maximum of GenTime.MAX
     *
     * @see GenTime#MAX
     * @return generated time object
     */
    T generate(final long from,
               final long to);
}
