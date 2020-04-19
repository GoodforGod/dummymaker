package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import io.dummymaker.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Generates LocalDateTime from 1970 to 3000 Year with seconds precision
 *
 * @author GoodforGod
 * @see LocalDateTime
 * @since 26.05.2017
 */
public class LocalDateTimeGenerator implements ITimeGenerator<LocalDateTime> {

    @Override
    public LocalDateTime generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public LocalDateTime generate(final long minUnix, final long maxUnix) {
        long usedFrom = minUnix;
        long usedTo = maxUnix;
        if (usedFrom < 0)
            usedFrom = 0;
        if (usedTo > GenTime.MAX_UNIX)
            usedTo = GenTime.MAX_UNIX;

        final long amount = (usedTo < usedFrom)
                ? usedFrom
                : CollectionUtils.random(usedFrom, usedTo);

        return LocalDateTime.ofEpochSecond(
                amount,
                0,
                ZoneOffset.UTC);
    }
}
