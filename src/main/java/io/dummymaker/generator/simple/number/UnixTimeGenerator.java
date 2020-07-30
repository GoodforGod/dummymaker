package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates Unix Time as long
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public class UnixTimeGenerator implements IGenerator<Long> {

    private final Pattern pattern = Pattern.compile("modif(y|ied)?|unix(time)?|epoch", CASE_INSENSITIVE);

    @Override
    public @NotNull Long generate() {
        return Instant.now().getEpochSecond() - CollectionUtils.random(99_000_000L);
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
