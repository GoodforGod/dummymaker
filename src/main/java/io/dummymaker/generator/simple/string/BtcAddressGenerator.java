package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates bitcoin address
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class BtcAddressGenerator extends IdGenerator {

    private static final Pattern PATTERN = Pattern.compile("btc|bitcoin", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return super.get().replace("-", "") + super.get().substring(0, 2).replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
