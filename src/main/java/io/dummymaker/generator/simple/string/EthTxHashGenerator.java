package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.UUID;
import java.util.regex.Pattern;

import io.dummymaker.generator.Generator;
import org.jetbrains.annotations.NotNull;

/**
 * Generates ethereum txhash
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class EthTxHashGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("eth(ereum)?tx(hash)?", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return "0x" + UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
