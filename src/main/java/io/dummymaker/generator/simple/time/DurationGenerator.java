package io.dummymaker.generator.simple.time;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
import java.time.Duration;

/**
 * @author Anton Kurako (GoodforGod)
 * @see Duration
 * @since 27.03.2023
 */
public final class DurationGenerator implements Generator<Duration> {

    private final long min;
    private final long max;

    public DurationGenerator() {
        this(1, 31_557_600_000L);
    }

    public DurationGenerator(long min, long max) {
        if (min < 1) {
            throw new IllegalArgumentException("Min can't be less than 1, but was: " + min);
        } else if (max < min) {
            throw new IllegalArgumentException("Max can't be less than Min, but was " + max + " when Min was " + min);
        }

        this.min = min;
        this.max = max;
    }

    @Override
    public Duration get() {
        return Duration.ofMillis(RandomUtils.random(min, max));
    }
}
