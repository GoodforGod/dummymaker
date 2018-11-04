package io.dummymaker.generator.simple.impl.string;

/**
 * Generates bitcoin address
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class BtcAddressGenerator extends IdGenerator {

    @Override
    public String generate() {
        return super.generate() + super.generate().substring(0, 2);
    }
}
