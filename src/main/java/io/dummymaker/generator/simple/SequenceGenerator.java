package io.dummymaker.generator.simple;

import io.dummymaker.annotation.special.GenSequence;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * Used to generate enumerated sequence for dummies Used by populate factory and genEnumerate
 *
 * @author GoodforGod
 * @see GenSequence
 * @since 07.06.2017
 */
public class SequenceGenerator implements IGenerator<Long> {

    private long counter;

    public SequenceGenerator() {
        this(0);
    }

    public SequenceGenerator(long initial) {
        this.counter = initial;
    }

    @Override
    public @NotNull Long generate() {
        return counter++;
    }
}
