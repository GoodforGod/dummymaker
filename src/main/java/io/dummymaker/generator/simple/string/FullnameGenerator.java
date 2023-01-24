package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates fullname
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class FullnameGenerator implements LocalizedGenerator<String> {

    private static final Pattern PATTERN = Pattern.compile("person|candidate|fullname|fio", CASE_INSENSITIVE);

    private static final LocalizedGenerator<String> NAME_GENERATOR = new NameGenerator();
    private static final LocalizedGenerator<String> MIDDLE_NAME_GENERATOR = new MiddleNameGenerator();
    private static final LocalizedGenerator<String> SURNAME_GENERATOR = new SurnameGenerator();

    @Override
    public @NotNull String get(@NotNull Localisation localisation) {
        return NAME_GENERATOR.get(localisation) + " " + MIDDLE_NAME_GENERATOR.get(localisation) + " "
                + SURNAME_GENERATOR.get(localisation);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -60;
    }
}
