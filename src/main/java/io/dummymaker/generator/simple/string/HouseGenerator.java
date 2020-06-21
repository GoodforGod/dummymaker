package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * House, block, room generator
 *
 * @author GoodforGod
 * @since 12.10.2019
 */
public class HouseGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("house|block|struct(ure)?|room|flat", CASE_INSENSITIVE);

    @Override
    public @NotNull String generate() {
        final String number = String.valueOf(CollectionUtils.random(1, 100));
        if (ThreadLocalRandom.current().nextDouble() > 0.33)
            return number;

        final char postfix = ThreadLocalRandom.current().nextBoolean()
                ? (char) CollectionUtils.random(65, 90)
                : (char) CollectionUtils.random(97, 122);

        return number + postfix;
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
