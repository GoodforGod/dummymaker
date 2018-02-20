package io.dummymaker.generator.impl.string;


import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates simple mobile phone as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class PhoneGenerator extends BigIdGenerator {

    @Override
    public String generate() {
        return current().nextInt(10)
                + "("
                + current().nextInt(100, 999)
                + ")"
                + current().nextInt(100, 999)
                + current().nextInt(1000, 9999);
    }
}
