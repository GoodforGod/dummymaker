package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates fullname
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class FullnameGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("person|candidate|fullname|fio", CASE_INSENSITIVE);

    private static final NameGenerator NAME_GENERATOR = new NameGenerator();
    private static final MiddleNameGenerator MIDDLE_NAME_GENERATOR = new MiddleNameGenerator();
    private static final SurnameGenerator SURNAME_GENERATOR = new SurnameGenerator();

    @Override
    public @NotNull String get() {
        return NAME_GENERATOR.get() + " " + MIDDLE_NAME_GENERATOR.get() + " " + SURNAME_GENERATOR.get();
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
