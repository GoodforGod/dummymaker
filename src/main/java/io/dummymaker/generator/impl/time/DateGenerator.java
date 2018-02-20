package io.dummymaker.generator.impl.time;

import io.dummymaker.generator.IGenerator;

import java.util.Date;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * generates old Date format object
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class DateGenerator implements IGenerator<Date> {

    @Override
    public Date generate() {
        return new Date(System.currentTimeMillis() - current().nextLong(0, 100000));
    }
}
