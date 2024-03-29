package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * House, block, room generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.10.2019
 */
public final class HouseGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("house|block|struct(ure)?|room|flat", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        final String number = String.valueOf(RandomUtils.random(1, 100));
        if (ThreadLocalRandom.current().nextDouble() > 0.33)
            return number;

        final char postfix = ThreadLocalRandom.current().nextBoolean()
                ? (char) RandomUtils.random(65, 90)
                : (char) RandomUtils.random(97, 122);

        return number + postfix;
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .build();
    }
}
