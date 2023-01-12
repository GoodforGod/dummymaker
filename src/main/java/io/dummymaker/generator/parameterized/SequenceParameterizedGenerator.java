package io.dummymaker.generator.parameterized;

import io.dummymaker.annotation.complex.GenSequence;
import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import java.util.concurrent.atomic.AtomicLong;
import org.jetbrains.annotations.NotNull;

/**
 * Used to generate enumerated sequence for dummies Used by populate factory and genEnumerate
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenSequence
 * @since 07.06.2017
 */
public final class SequenceParameterizedGenerator implements ParameterizedGenerator<Long> {

    private final AtomicLong counter;

    public SequenceParameterizedGenerator(long initial) {
        this.counter = new AtomicLong(initial);
    }

    @Override
    public Long get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        return get();
    }

    @Override
    public @NotNull Long get() {
        return counter.getAndIncrement();
    }
}
