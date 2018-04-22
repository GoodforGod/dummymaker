package io.dummymaker.generator.simple.impl.number;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates big double from 10 to 1000000 value
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class DoubleBigGenerator extends DoubleGenerator {

    @Override
    public Double generate(){
        return super.generate() * ThreadLocalRandom.current().nextInt(10, 1000000);
    }
}
