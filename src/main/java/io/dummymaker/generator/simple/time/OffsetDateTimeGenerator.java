package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import io.dummymaker.util.CollectionUtils;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author GoodforGod
 * @see OffsetDateTime
 * @since 05.12.2021
 */
public class OffsetDateTimeGenerator implements ITimeGenerator<OffsetDateTime> {

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull OffsetDateTime generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull OffsetDateTime generate(final long minUnix, final long maxUnix) {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(CollectionUtils.random(-18, 18));
        return OffsetDateTime.of(generator.generate(), zoneOffset);
    }
}
