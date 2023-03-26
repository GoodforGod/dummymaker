package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.parameterized.GenTime;
import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
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
public final class OffsetTimeGenerator implements Generator<OffsetTime> {

    private final LocalTimeGenerator localTimeGenerator;

    public OffsetTimeGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public OffsetTimeGenerator(long from, long to) {
        this.localTimeGenerator = new LocalTimeGenerator(from, to);
    }

    @Override
    public @NotNull OffsetTime get() {
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(RandomUtils.random(-18, 18));
        return OffsetTime.of(localTimeGenerator.get(), zoneOffset);
    }
}
