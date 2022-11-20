package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.number.LongGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates number represented as hex string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class HexNumberGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("hex(num(ber)?)?", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return Long.toHexString(new LongGenerator().get());
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
