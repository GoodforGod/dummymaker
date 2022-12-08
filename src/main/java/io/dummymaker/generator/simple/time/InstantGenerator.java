package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.UnixTimeGenerator;
import io.dummymaker.util.CollectionUtils;
import java.time.Instant;
import java.time.ZoneOffset;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see Instant
 * @since 21.10.2022
 */
public final class InstantGenerator implements UnixTimeGenerator<Instant> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull Instant get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Instant generate(final long fromUnixTime, final long toUnixTime) {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(RandomUtils.random(-18, 18));
        return generator.generate(fromUnixTime, toUnixTime).toInstant(zoneOffset);
    }
}
