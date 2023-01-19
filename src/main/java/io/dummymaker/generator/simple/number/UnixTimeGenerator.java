package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
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

    public UnixTimeGenerator(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public @NotNull Long get() {
        return RandomUtils.random(from, to);
    }
}
