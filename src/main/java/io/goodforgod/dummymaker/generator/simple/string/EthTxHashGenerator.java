package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import java.util.UUID;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates ethereum txhash
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class EthTxHashGenerator implements Generator<CharSequence> {

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
