package io.goodforgod.dummymaker.generator.simple.number;

import io.goodforgod.dummymaker.annotation.simple.number.GenSequence;
import io.goodforgod.dummymaker.generator.Generator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Used to generate enumerated sequence for dummies Used by populate factory and genEnumerate
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenSequence
 * @since 07.06.2017
 */
public final class SequenceGenerator implements Generator<Long> {

    private final AtomicLong counter;

    public SequenceGenerator(long initial) {
        this.counter = new AtomicLong(initial);
    }

    @Override
    public Long get() {
        return counter.getAndIncrement();
    }
}
