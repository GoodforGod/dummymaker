package io.dummymaker.generator.simple;

import io.dummymaker.annotation.complex.GenSequence;
import io.dummymaker.generator.Generator;
import org.jetbrains.annotations.NotNull;

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

    public SequenceGenerator() {
        this(0);
    }

    public SequenceGenerator(long initial) {
        this.counter = new AtomicLong(initial);
    }

    @Override
    public @NotNull Long get() {
        return counter.getAndIncrement();
    }
}
