package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.UnixTimeGenerator;
import io.dummymaker.util.CollectionUtils;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see OffsetDateTime
 * @since 05.12.2021
 */
public final class OffsetDateTimeGenerator implements UnixTimeGenerator<OffsetDateTime> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull OffsetDateTime get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull OffsetDateTime generate(final long fromUnixTime, final long toUnixTime) {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(RandomUtils.random(-18, 18));
        return OffsetDateTime.of(generator.generate(fromUnixTime, toUnixTime), zoneOffset);
    }
}
