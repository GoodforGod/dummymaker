package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.util.CollectionUtils;
import java.util.UUID;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates hex data
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class HexDataGenerator extends IdBigGenerator {

    private final Pattern pattern = Pattern.compile("hex", CASE_INSENSITIVE);

    @Override
    public @NotNull String generate() {
        final StringBuilder builder = new StringBuilder();
        final int total = CollectionUtils.random(1, 5);
        for (int i = 0; i < total; i++)
            builder.append(UUID.randomUUID());
        return builder.toString().replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
