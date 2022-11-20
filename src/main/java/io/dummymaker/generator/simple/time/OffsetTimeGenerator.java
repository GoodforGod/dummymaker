package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.UnixTimeGenerator;
import io.dummymaker.util.CollectionUtils;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import org.jetbrains.annotations.NotNull;

/**
 * Generates localTime object with range from 00:00:00 to 24:00:00
 *
 * @author Anton Kurako (GoodforGod)
 * @see OffsetTime
 * @since 05.12.2021
 */
public final class OffsetTimeGenerator implements UnixTimeGenerator<OffsetTime> {

    private static final LocalTimeGenerator generator = new LocalTimeGenerator();

    @Override
    public @NotNull OffsetTime get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull OffsetTime generate(final long minUnix, final long maxUnix) {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(RandomUtils.random(-18, 18));
        return OffsetTime.of(generator.generate(minUnix, maxUnix), zoneOffset);
    }
}
