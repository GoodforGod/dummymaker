package io.dummymaker.generator.simple.impl.string;


import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates password as a string, from 6 to 31 length
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class PassGenerator extends IdBigGenerator {

    @Override
    public String generate() {
        int passLength = ThreadLocalRandom.current().nextInt(6, 31);
        return super.generate().substring(0, passLength);
    }
}
