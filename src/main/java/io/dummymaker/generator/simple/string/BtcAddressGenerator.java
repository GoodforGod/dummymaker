package io.dummymaker.generator.simple.string;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;


/**
 * Generates bitcoin address
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class BtcAddressGenerator extends IdGenerator {

    private final Pattern pattern = Pattern.compile("btc|bitcoin", CASE_INSENSITIVE);

    @Override
    public @NotNull String generate() {
        return super.generate().replace("-", "") + super.generate().substring(0, 2).replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
