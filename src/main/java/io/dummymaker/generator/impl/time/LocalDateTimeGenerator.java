package io.dummymaker.generator.impl.time;

import io.dummymaker.generator.IGenerator;

import java.time.LocalDateTime;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates LocalDateTime from 1970 to 3000 Year with nanoseconds precision
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class LocalDateTimeGenerator implements IGenerator<LocalDateTime> {

    @Override
    public LocalDateTime generate() {
        int year        = current().nextInt(1970, 3000);
        int month       = current().nextInt(1, 12);
        int dayOfMonth  = (month == 2)
                ? current().nextInt(1,27)
                : current().nextInt(1, 31);
        int hour        = current().nextInt(1, 24);
        int minute      = current().nextInt(1, 60);
        int second      = current().nextInt(1, 60);
        int nanoOfSecond = current().nextInt(1, 999999999);

        return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
    }
}
