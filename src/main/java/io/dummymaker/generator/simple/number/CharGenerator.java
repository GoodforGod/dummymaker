package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates random UTF-8 character in range of 33-254
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class CharGenerator implements Generator<Character> {

    @Override
    public @NotNull Character get() {
        return ((char) RandomUtils.random(33, 255));
    }
}
