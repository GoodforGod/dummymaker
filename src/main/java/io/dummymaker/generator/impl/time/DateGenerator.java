package io.dummymaker.generator.impl.time;

import io.dummymaker.generator.IGenerator;

import java.util.Date;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates old java date type
 * This date is exported in long milliseconds format
 * So date is the milliseconds since January 1, 1970, 00:00:00 GMT
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class DateGenerator implements IGenerator<Date> {

    @Override
    public Date generate() {
        return new Date(System.currentTimeMillis() - current().nextLong(0, 10000000));
    }
}
