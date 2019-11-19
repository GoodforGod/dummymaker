package io.dummymaker.generator.simple.string;

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
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String generate() {
        return super.generate().replace("-", "") + super.generate().replace("-", "");
    }
}
