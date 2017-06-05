package io.dummymaker.generate;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class BigDoubleGenerator extends DoubleGenerator {

    @Override
    public Double generate() {
        return super.generate() * current().nextInt(100, 1000000);
    }
}
