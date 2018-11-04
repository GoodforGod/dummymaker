package io.dummymaker.generator.simple.impl.number;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates float number from 0 to 10000
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class FloatBigGenerator extends FloatGenerator {

    @Override
    public Float generate() {
        return super.generate() * ThreadLocalRandom.current().nextInt(10, 10000);
    }
}
