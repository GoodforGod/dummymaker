package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import io.dummymaker.util.CollectionUtils;
import java.time.Instant;
import java.time.ZoneOffset;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author GoodforGod
 * @see Instant
 * @since 21.10.2022
 */
public class InstantGenerator implements ITimeGenerator<Instant> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull Instant generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Instant generate(final long minUnix, final long maxUnix) {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(CollectionUtils.random(-18, 18));
        return generator.generate(minUnix, maxUnix).toInstant(zoneOffset);
    }
}
