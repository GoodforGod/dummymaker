package io.dummymaker.generator.simple.impl.string;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates ethereum address
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class EthAddressGenerator extends IdGenerator {

    private final Pattern pattern = Pattern.compile("eth(ereum)?|etc", CASE_INSENSITIVE);

    @Override
    public String generate() {
        return "0x" + super.generate() + super.generate().substring(0, 8);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
