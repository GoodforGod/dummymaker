package io.goodforgod.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Unix Time as long
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public final class UnixTimeGenerator implements Generator<Long> {

    private static final Pattern PATTERN = Pattern.compile("modif(y|ied)?|unix(time)?|epoch", CASE_INSENSITIVE);

    private final long from;
    private final long to;

    public UnixTimeGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public UnixTimeGenerator(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .build();
    }

    @Override
    public @NotNull Long get() {
        return RandomUtils.random(from, to);
    }
}
