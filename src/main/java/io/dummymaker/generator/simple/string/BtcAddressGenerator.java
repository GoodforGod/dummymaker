package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import java.util.UUID;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates bitcoin address
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class BtcAddressGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("btc|bitcoin", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().substring(0, 2).replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
