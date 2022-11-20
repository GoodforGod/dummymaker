package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates short from 0 to Short.MAX_VALUE
 *
 * @author Anton Kurako (GoodforGod)
 * @see Short#MAX_VALUE
 * @since 05.03.2019
 */
public final class ShortGenerator implements Generator<Short> {

    @Override
    public @NotNull Short get() {
        return ((short) RandomUtils.random(Short.MAX_VALUE));
    }
}
