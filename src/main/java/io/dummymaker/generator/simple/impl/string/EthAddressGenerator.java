package io.dummymaker.generator.simple.impl.string;

/**
 * Generates ethereum address
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class EthAddressGenerator extends IdGenerator {

    @Override
    public String generate() {
        return "0x" + super.generate() + super.generate().substring(0, 7);
    }
}
