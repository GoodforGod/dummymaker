package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.TimeGenerator;

import java.util.Date;

import io.dummymaker.generator.simple.number.UnixTimeGenerator;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates old java date type This date is exported in long milliseconds format So date is the
 * milliseconds since January 1, 1970, 00:00:00 GMT
 *
 * @author Anton Kurako (GoodforGod)
 * @see Date
 * @since 21.02.2018
 */
public final class DateGenerator implements TimeGenerator<Date> {

    private static final UnixTimeGenerator UNIX_TIME_GENERATOR = new UnixTimeGenerator();

    @Override
    public @NotNull Date get() {
        return get(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Date get(long fromUnixTime, long toUnixTime) {
        final long unixTime = UNIX_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        return new Date(unixTime);
    }
}
