package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.LongGenerator;

/**
 * Generates number represented as hex string
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class HexNumberGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        return Long.toHexString(new LongGenerator().generate());
    }
}
