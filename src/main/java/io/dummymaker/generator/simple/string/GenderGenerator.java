package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates gender as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public final class GenderGenerator implements LocalizedGenerator<String> {

    private static final Pattern PATTERN = Pattern.compile("sex|gender", CASE_INSENSITIVE);

    @Override
    public @NotNull String get(@NotNull Localisation localisation) {
        switch (localisation) {
            case RUSSIAN:
                return ThreadLocalRandom.current().nextBoolean()
                        ? "мужчина"
                        : "женщина";
            default:
                return ThreadLocalRandom.current().nextBoolean()
                        ? "male"
                        : "female";
        }
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
