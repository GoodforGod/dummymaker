package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates ethereum address
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class EthAddressGenerator extends IdGenerator {

    private static final Pattern PATTERN = Pattern.compile("eth(ereum)?|etc", CASE_INSENSITIVE);

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public @NotNull String get() {
        return "0x" + super.get().replace("-", "") + super.get().substring(0, 8).replace("-", "");
    }
}
