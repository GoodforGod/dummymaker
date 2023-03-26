package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.parameterized.GenTime;
import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
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
public final class InstantGenerator implements Generator<Instant> {

    private final LocalDateTimeGenerator localDateTimeGenerator;

    public InstantGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public InstantGenerator(long from, long to) {
        this.localDateTimeGenerator = new LocalDateTimeGenerator(from, to);
    }

    @Override
    public @NotNull Instant get() {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(RandomUtils.random(-18, 18));
        return localDateTimeGenerator.get().toInstant(zoneOffset);
    }
}
