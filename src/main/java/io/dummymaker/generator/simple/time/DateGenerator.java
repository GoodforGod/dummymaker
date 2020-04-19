package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import io.dummymaker.util.CollectionUtils;

import java.util.Date;

/**
 * Generates old java date type This date is exported in long milliseconds
 * format So date is the milliseconds since January 1, 1970, 00:00:00 GMT
 *
 * @author GoodforGod
 * @see Date
 * @since 21.02.2018
 */
public class DateGenerator implements ITimeGenerator<Date> {

    @Override
    public Date generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public Date generate(final long minUnix, final long maxUnix) {
        long usedFrom = minUnix;
        long usedTo = maxUnix;
        if (usedFrom < 0)
            usedFrom = 0;
        if (usedTo > GenTime.MAX_UNIX)
            usedTo = GenTime.MAX_UNIX;

        final long amount = (usedTo < usedFrom)
                ? usedFrom
                : CollectionUtils.random(usedFrom, usedTo);

        return new Date(amount);
    }
}
