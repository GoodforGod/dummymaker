package io.goodforgod.dummymaker.generator.simple.time;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.time.ZoneOffset;
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
