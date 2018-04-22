package io.dummymaker.generator.simple.impl.time.impl;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.simple.impl.time.ITimeGenerator;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates old java date type
 * This date is exported in long milliseconds format
 * So date is the milliseconds since January 1, 1970, 00:00:00 GMT
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class DateGenerator implements ITimeGenerator<Date> {

    @Override
    public Date generate() {
        return generate(0, GenTime.MAX);
    }

    @Override
    public Date generate(final long from, final long to) {
        long usedFrom = from;
        long usedTo = to;
        if(usedFrom < 0)
            usedFrom = 0;
        if(usedTo > GenTime.MAX)
            usedTo = GenTime.MAX;

        final long amount = (usedTo < usedFrom)
                ? usedFrom
                : ThreadLocalRandom.current().nextLong(usedFrom, usedTo);

        return new Date(amount);
    }
}
