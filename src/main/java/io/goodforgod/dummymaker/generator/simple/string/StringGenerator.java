package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.Random;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates random string like "aag2151tgdsfa9352tf"
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class StringGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("str|string", CASE_INSENSITIVE);
    private static final int LEFT_LIMIT = 48; // numeral '0'
    private static final int RIGHT_LIMIT = 122; // letter 'z'

    private final Random random = new Random();
    private final int min;
    private final int max;

    public StringGenerator() {
        this(6, 12);
    }

    public StringGenerator(int min, int max) {
        if (min < 1) {
            throw new IllegalArgumentException("Min can't be less than 1, but was: " + min);
        } else if (max < min) {
            throw new IllegalArgumentException("Max can't be less than Min, but was " + max + " when Min was " + min);
        }

        this.min = min;
        this.max = max;
    }

    @Override
    public @NotNull String get() {
        final int length = RandomUtils.random(min, max);
        return random.ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .build();
    }
}
