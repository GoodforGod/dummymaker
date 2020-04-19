package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;

/**
 * Generates short from 0 to Short.MAX_VALUE
 *
 * @author GoodforGod
 * @see Short#MAX_VALUE
 * @since 05.03.2019
 */
public class ShortGenerator implements IGenerator<Short> {

    @Override
    public Short generate() {
        return ((short) CollectionUtils.random(Short.MAX_VALUE));
    }
}
