package io.dummymaker.generate;

import java.time.LocalDateTime;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class LocalDateTimeGenerator implements IGenerator<LocalDateTime> {

    @Override
    public LocalDateTime generate() {
        int year        = current().nextInt(1970, 3000);
        int month       = current().nextInt(1, 12);
        int dayOfMonth  = current().nextInt(1, 31);
        int hour        = current().nextInt(1, 60);
        int minute      = current().nextInt(1, 60);
        int second      = current().nextInt(1, 60);
        int nanoOfSecond = current().nextInt(1, 999);

        return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
    }
}
