package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates gender as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public final class GenderGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("sex|gender", CASE_INSENSITIVE);

    @Override
    public String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(get(parameters.localisation())).toString();
    }

    @Override
    public String get() {
        return get(Localisation.ENGLISH);
    }

    private static String get(@NotNull Localisation localisation) {
        if (localisation == Localisation.RUSSIAN) {
            return ThreadLocalRandom.current().nextBoolean()
                    ? "мужчина"
                    : "женщина";
        }

        return ThreadLocalRandom.current().nextBoolean()
                ? "male"
                : "female";
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
