package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.parameterized.GenTime;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.number.UnixTimeGenerator;
import java.util.Date;
import org.jetbrains.annotations.NotNull;

/**
 * Generates old java date type This date is exported in long milliseconds format So date is the
 * milliseconds since January 1, 1970, 00:00:00 GMT
 *
 * @author Anton Kurako (GoodforGod)
 * @see Date
 * @since 21.02.2018
 */
public final class DateGenerator implements Generator<Date> {

    private final UnixTimeGenerator unixTimeGenerator;

    public DateGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public DateGenerator(long from, long to) {
        this.unixTimeGenerator = new UnixTimeGenerator(from, to);
    }

    @Override
    public @NotNull Date get() {
        return new Date(unixTimeGenerator.get());
    }
}
