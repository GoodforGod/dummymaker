package io.dummymaker.generator.simple.string;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates bitcoin txhash or block hash
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class BtcTxHashGenerator extends IdGenerator {

    private final Pattern pattern = Pattern.compile("tx|btctx|bitcointx|btchash|bictoinhash", CASE_INSENSITIVE);

    @Override
    public @NotNull String generate() {
        return super.generate().replace("-", "") + super.generate().replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
