package io.dummymaker.generator.simple.impl.string;

/**
 * Generates ethereum txhash
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class EthTxHashGenerator extends IdGenerator {

    @Override
    public String generate() {
        return "0x" + super.generate() + super.generate();
    }
}
