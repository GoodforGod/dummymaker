package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates ethereum txhash
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class EthTxHashGenerator extends IdGenerator {

    private static final Pattern PATTERN = Pattern.compile("eth(ereum)?tx(hash)?", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return "0x" + super.get().replace("-", "") + super.get().replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
