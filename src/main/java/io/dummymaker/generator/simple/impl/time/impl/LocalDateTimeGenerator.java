package io.dummymaker.generator.simple.impl.time.impl;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.simple.impl.time.ITimeGenerator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates LocalDateTime from 1970 to 3000 Year with seconds precision
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class LocalDateTimeGenerator implements ITimeGenerator<LocalDateTime> {

    @Override
    public LocalDateTime generate() {
        return generate(0, GenTime.MAX);
    }

    @Override
    public LocalDateTime generate(final long from, final long to) {
        long usedFrom = from;
        long usedTo = to;
        if (usedFrom < 0)
            usedFrom = 0;
        if (usedTo > GenTime.MAX)
            usedTo = GenTime.MAX;

        final long amount = (usedTo < usedFrom)
                ? usedFrom
                : ThreadLocalRandom.current().nextLong(usedFrom, usedTo);

        return LocalDateTime.ofEpochSecond(
                amount,
                0,
                ZoneOffset.UTC
        );
    }
}
