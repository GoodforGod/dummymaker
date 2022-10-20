package io.dummymaker.generator.simple.time;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import java.time.ZoneOffset;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author GoodforGod
 * @see ZoneOffset
 * @since 21.10.2022
 */
public class ZonedOffsetGenerator implements IGenerator<ZoneOffset> {

    @Override
    public @NotNull ZoneOffset generate() {
        return ZoneOffset.ofHours(CollectionUtils.random(-18, 18));
    }
}
