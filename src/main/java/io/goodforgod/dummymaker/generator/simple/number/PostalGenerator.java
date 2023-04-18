package io.goodforgod.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates medium integer values between 100000 and 999999
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.10.2019
 */
public final class PostalGenerator implements Generator<Integer> {

    private static final Pattern PATTERN = Pattern.compile("number|postal|code|index|zip(code)?", CASE_INSENSITIVE);

    @Override
    public @NotNull Integer get() {
        return RandomUtils.random(100000, 999999);
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .withPriority(50)
                .build();
    }
}
