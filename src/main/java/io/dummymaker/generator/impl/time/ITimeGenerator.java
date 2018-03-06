package io.dummymaker.generator.impl.time;

import io.dummymaker.generator.IGenerator;

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
     * @see io.dummymaker.annotation.time.GenTime#MAX
     */
    T generate(final long from,
               final long to);
}
