package io.dummymaker.generator.simple.time;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.time.ZoneOffset;

import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see ZoneOffset
 * @since 21.10.2022
 */
public final class ZonedOffsetGenerator implements Generator<ZoneOffset> {

    @Override
    public @NotNull ZoneOffset get() {
        return ZoneOffset.ofHours(RandomUtils.random(-18, 18));
    }
}
