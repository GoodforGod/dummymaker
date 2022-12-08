package io.dummymaker.generator;

import io.dummymaker.annotation.complex.GenTime;

/**
 * Generates time/datetime/timestamp in range if necessary
 *
 * @author Anton Kurako (GoodforGod)
 * @since 06.03.2018
 */
public interface UnixTimeGenerator<T> extends Generator<T> {

    /**
     * @param fromUnixTime minimum 0 represents {@link GenTime#MIN_UNIX}
     * @param toUnixTime as a maximum of {@link GenTime#MAX_UNIX}
     * @return generated time object
     */
    T generate(long fromUnixTime, long toUnixTime);
}
