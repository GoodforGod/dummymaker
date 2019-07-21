package io.dummymaker.generator.simple.impl;

import io.dummymaker.annotation.special.GenSequence;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Used to generate enumerated sequence for dummies
 * Used by populate factory and genEnumerate
 *
 * @author GoodforGod
 * @see io.dummymaker.factory.IPopulateFactory
 * @see GenSequence
 * @since 07.06.2017
 */
public class SequenceGenerator implements IGenerator<Object> {

    private long counter;

    public SequenceGenerator() {
        this(0);
    }

    public SequenceGenerator(long initial) {
        this.counter = initial;
    }

    @Override
    public Object generate() {
        return counter++;
    }
}
