package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.UnixTimeGenerator;
import io.dummymaker.util.CollectionUtils;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see ZonedDateTime
 * @since 21.10.2022
 */
public final class ZonedDateTimeGenerator implements UnixTimeGenerator<ZonedDateTime> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull ZonedDateTime get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull ZonedDateTime generate(final long minUnix, final long maxUnix) {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(RandomUtils.random(-18, 18));
        return ZonedDateTime.of(generator.generate(minUnix, maxUnix), zoneOffset);
    }
}
