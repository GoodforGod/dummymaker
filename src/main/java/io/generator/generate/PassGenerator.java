package io.generator.generate;


import java.util.concurrent.ThreadLocalRandom;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class PassGenerator extends StringGenerator {

    @Override
    public String generate() {
        int passLength = ThreadLocalRandom.current().nextInt(6, 31);
        return super.generate().substring(0, passLength);
    }
}
