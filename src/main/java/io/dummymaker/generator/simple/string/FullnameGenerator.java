package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.*;
import io.dummymaker.generator.GenParameters;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.ParameterizedGenerator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates fullname
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class FullnameGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("person|candidate|fullname|fio", CASE_INSENSITIVE);

    private static final Bundle MALE_BUNDLE = new MaleNameBundle();
    private static final Bundle FEMALE_BUNDLE = new FemaleNameBundle();
    private static final Bundle MIDDLE_NAME_BUNDLE = new MiddleNameBundle();
    private static final Bundle SURNAME_BUNDLE = new SurnameBundle();

    @Override
    public String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(get(parameters.localisation())).toString();
    }

    @Override
    public String get() {
        return get(Localisation.ENGLISH);
    }

    private static String get(Localisation localisation) {
        final String firstName = ThreadLocalRandom.current().nextBoolean()
                ? MALE_BUNDLE.random(localisation)
                : FEMALE_BUNDLE.random(localisation);

        return firstName + " "
                + MIDDLE_NAME_BUNDLE.random(localisation) + " "
                + SURNAME_BUNDLE.random(localisation);
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
