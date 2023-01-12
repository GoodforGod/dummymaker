package io.dummymaker.generator;

import io.dummymaker.annotation.complex.GenTime;
import org.jetbrains.annotations.Range;

/**
 * Generates time/datetime/timestamp in range if necessary
 *
 * @author Anton Kurako (GoodforGod)
 * @since 06.03.2018
 */
public interface TimeGenerator<T> extends Generator<T> {

    /**
     * @param fromUnixTime minimum 0 represents {@link GenTime#MIN_UNIX}
     * @param toUnixTime   as a maximum of {@link GenTime#MAX_UNIX}
     * @return generated time object
     */
    T get(@Range(from = GenTime.MIN_UNIX, to = GenTime.MAX_UNIX) long fromUnixTime,
          @Range(from = GenTime.MIN_UNIX, to = GenTime.MAX_UNIX) long toUnixTime);
}
