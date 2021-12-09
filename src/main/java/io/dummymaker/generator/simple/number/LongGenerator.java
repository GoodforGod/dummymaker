package io.dummymaker.generator.simple.number;


import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;


/**
 * Long positive number generator
 *
 * @author GoodforGod
 * @see Long#MAX_VALUE
 * @since 15.09.2019
 */
public class LongGenerator implements IGenerator<Long> {

    @Override
    public @NotNull Long generate() {
        return CollectionUtils.random(Long.MAX_VALUE);
    }
}
