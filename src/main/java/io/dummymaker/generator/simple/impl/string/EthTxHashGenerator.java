package io.dummymaker.generator.simple.impl.string;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates ethereum txhash
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class EthTxHashGenerator extends IdGenerator {

    private final Pattern pattern = Pattern.compile("eth(ereum)?tx(hash)?", CASE_INSENSITIVE);

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String generate() {
        return "0x" + super.generate().replace("-", "") + super.generate().replace("-", "");
    }
}
