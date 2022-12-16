package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.TimeGenerator;

import java.time.Instant;
import java.time.ZoneOffset;

import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see Instant
 * @since 21.10.2022
 */
public final class InstantGenerator implements TimeGenerator<Instant> {

    private static final LocalDateTimeGenerator GENERATOR = new LocalDateTimeGenerator();

    @Override
    public @NotNull Instant get() {
        return get(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Instant get(long fromUnixTime, long toUnixTime) {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(RandomUtils.random(-18, 18));
        return GENERATOR.get(fromUnixTime, toUnixTime).toInstant(zoneOffset);
    }
}
