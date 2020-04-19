package io.dummymaker.generator.simple.number;

import io.dummymaker.util.CollectionUtils;

/**
 * Generates float number from 0 to 10000
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class FloatBigGenerator extends FloatGenerator {

    @Override
    public Float generate() {
        return super.generate() * CollectionUtils.random(10, 10000);
    }
}
