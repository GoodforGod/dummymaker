package io.dummymaker.generator.simple.number;

import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates big double from 10 to 1000000 value
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class DoubleBigGenerator extends DoubleGenerator {

    @Override
    public @NotNull Double get() {
        return super.get() * RandomUtils.random(10, 1000000);
    }
}
