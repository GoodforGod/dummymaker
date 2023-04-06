package io.goodforgod.dummymaker.generator.simple.time;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
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
public final class ZonedDateTimeGenerator implements Generator<ZonedDateTime> {

    private final LocalDateTimeGenerator localDateTimeGenerator;

    public ZonedDateTimeGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public ZonedDateTimeGenerator(long from, long to) {
        this.localDateTimeGenerator = new LocalDateTimeGenerator(from, to);
    }

    @Override
    public @NotNull ZonedDateTime get() {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(RandomUtils.random(-18, 18));
        return ZonedDateTime.of(localDateTimeGenerator.get(), zoneOffset);
    }
}
