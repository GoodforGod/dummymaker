package io.dummymaker.generator.simple.impl;

import io.dummymaker.annotation.special.GenSequential;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Used to generate enumerated sequence for dummies
 * Used by populate factory and genEnumerate
 *
 * @see io.dummymaker.factory.IPopulateFactory
 * @see GenSequential
 *
 * @author GoodforGod
 * @since 07.06.2017
 */
public class SequentialGenerator implements IGenerator<Object> {

    @Override
    public Object generate() {
        return null;
    }
}
