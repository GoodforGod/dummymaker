package io.dummymaker.generator.simple.impl.string;

/**
 * Generates bitcoin txhash or block hash
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class BtcTxHashGenerator extends IdGenerator {

    @Override
    public String generate() {
        return super.generate() + super.generate();
    }
}
