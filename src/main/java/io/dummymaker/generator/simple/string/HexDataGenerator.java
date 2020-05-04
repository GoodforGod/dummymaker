package io.dummymaker.generator.simple.string;

import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

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
        return (super.generate() + super.generate()).substring(0, CollectionUtils.random(16, 128));
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
