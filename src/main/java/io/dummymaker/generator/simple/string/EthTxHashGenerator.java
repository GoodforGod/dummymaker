package io.dummymaker.generator.simple.string;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;


/**
 * Generates ethereum txhash
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class EthTxHashGenerator extends IdGenerator {

    private final Pattern pattern = Pattern.compile("eth(ereum)?tx(hash)?", CASE_INSENSITIVE);

    @Override
    public @NotNull String generate() {
        return "0x" + super.generate().replace("-", "") + super.generate().replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
