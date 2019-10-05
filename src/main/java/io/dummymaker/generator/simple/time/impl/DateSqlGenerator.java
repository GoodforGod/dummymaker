package io.dummymaker.generator.simple.time.impl;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.simple.time.ITimeGenerator;

import java.sql.Date;

/**
 * Generates Sql Time from 1970 to 3000 Year
 *
 * @author GoodforGod
 * @see Date
 * @since 10.03.2019
 */
public class DateSqlGenerator implements ITimeGenerator<Date> {

    private final LocalDateGenerator generator = new LocalDateGenerator();

    @Override
    public Date generate() {
        return generate(0, GenTime.MAX);
    }

    @Override
    public Date generate(long from, long to) {
        return Date.valueOf(generator.generate(from, to));
    }
}
