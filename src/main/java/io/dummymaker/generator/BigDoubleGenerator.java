package io.dummymaker.generator;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates big double from 10 to 1000000 value
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class BigDoubleGenerator extends DoubleGenerator {

    @Override
    public Double generate(){
        return super.generate() * current().nextInt(10, 1000000);
    }
}
