package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates byte from -127 to 128
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class ByteGenerator implements IGenerator<Byte> {

    @Override
    public @NotNull Byte generate() {
        return (byte) CollectionUtils.random(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }
}
