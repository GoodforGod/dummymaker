package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates ethereum address
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class EthAddressGenerator extends IdGenerator {

    private final Pattern pattern = Pattern.compile("eth(ereum)?|etc", CASE_INSENSITIVE);

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }

    @Override
    public @NotNull String generate() {
        return "0x" + super.generate().replace("-", "") + super.generate().substring(0, 8).replace("-", "");
    }
}
