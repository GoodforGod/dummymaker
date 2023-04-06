package io.goodforgod.dummymaker.generator.simple.time;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
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
public final class OffsetDateTimeGenerator implements Generator<OffsetDateTime> {

    private final LocalDateTimeGenerator localDateTimeGenerator;

    public OffsetDateTimeGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public OffsetDateTimeGenerator(long from, long to) {
        this.localDateTimeGenerator = new LocalDateTimeGenerator(from, to);
    }

    @Override
    public @NotNull OffsetDateTime get() {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(RandomUtils.random(-18, 18));
        return OffsetDateTime.of(localDateTimeGenerator.get(), zoneOffset);
    }
}
