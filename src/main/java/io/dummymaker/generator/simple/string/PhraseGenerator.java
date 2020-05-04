package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.PhraseBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates some english phrase as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class PhraseGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("phrase", CASE_INSENSITIVE);

    private final IBundle<String> bundle = new PhraseBundle();

    @Override
    public @NotNull String generate() {
        return bundle.getRandom();
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
