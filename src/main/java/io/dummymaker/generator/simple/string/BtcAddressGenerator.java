package io.dummymaker.generator.simple.string;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates bitcoin address
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class BtcAddressGenerator extends IdGenerator {

    private final Pattern pattern = Pattern.compile("btc|bitcoin", CASE_INSENSITIVE);

    @NotNull
    @Override
    public String generate() {
        return super.generate().replace("-", "") + super.generate().substring(0, 2).replace("-", "");
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
