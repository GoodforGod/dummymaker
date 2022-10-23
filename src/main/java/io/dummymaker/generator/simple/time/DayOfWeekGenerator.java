package io.dummymaker.generator.simple.time;

import io.dummymaker.generator.IGenerator;
import java.time.DayOfWeek;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author GoodforGod
 * @see DayOfWeek
 * @since 21.10.2022
 */
public class DayOfWeekGenerator implements IGenerator<DayOfWeek> {

    @Override
    public @NotNull DayOfWeek generate() {
        return DayOfWeek.of(ThreadLocalRandom.current().nextInt(1, 8));
    }
}
