package io.dummymaker.generator.simple.time;

import io.dummymaker.generator.Generator;
import java.time.Year;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see Year
 * @since 21.10.2022
 */
public final class YearGenerator implements Generator<Year> {

    private final LocalDateTimeGenerator localDateTimeGenerator;

    public YearGenerator(long from, long to) {
        this.localDateTimeGenerator = new LocalDateTimeGenerator(from, to);
    }

    @Override
    public @NotNull Year get() {
        return Year.of(localDateTimeGenerator.get().getYear());
    }
}
