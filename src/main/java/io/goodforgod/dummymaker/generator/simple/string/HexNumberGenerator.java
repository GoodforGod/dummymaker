package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates number represented as hex string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class HexNumberGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("hex(num(ber)?)?", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return Long.toHexString(RandomUtils.random(Long.MAX_VALUE));
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
