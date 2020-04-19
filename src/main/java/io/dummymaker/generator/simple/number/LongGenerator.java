package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;

/**
 * Long positive number generator
 *
 * @author GoodforGod
 * @see Long#MAX_VALUE
 * @since 15.09.2019
 */
public class LongGenerator implements IGenerator<Long> {

    @Override
    public Long generate() {
        return CollectionUtils.random(Long.MAX_VALUE);
    }
}
