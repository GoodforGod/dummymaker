package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates short from 0 to Short.MAX_VALUE
 *
 * @author GoodforGod
 * @see Short#MAX_VALUE
 * @since 05.03.2019
 */
public class UShortGenerator implements IGenerator<Short> {

    @Override
    public Short generate() {
        return ((short) ThreadLocalRandom.current().nextInt(0, Short.MAX_VALUE));
    }
}
