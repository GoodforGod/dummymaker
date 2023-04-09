package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import java.util.UUID;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates ethereum address
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class EthAddressGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("eth(ereum)?|etc", CASE_INSENSITIVE);

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .build();
    }

    @Override
    public @NotNull String get() {
        return "0x" + UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().substring(0, 8).replace("-", "");
    }
}
