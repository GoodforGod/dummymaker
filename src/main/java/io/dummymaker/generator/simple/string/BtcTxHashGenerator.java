package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates bitcoin txhash or block hash
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class BtcTxHashGenerator extends IdGenerator {

    private static final Pattern PATTERN = Pattern.compile("tx|btctx|bitcointx|btchash|bictoinhash", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return super.get().replace("-", "") + super.get().replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
