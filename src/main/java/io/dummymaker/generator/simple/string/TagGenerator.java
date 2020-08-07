package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.TagsBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates tag as a string like #tag
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class TagGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("(hash)?tag", CASE_INSENSITIVE);

    private final IBundle bundle = new TagsBundle();

    @Override
    public @NotNull String generate() {
        return bundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
