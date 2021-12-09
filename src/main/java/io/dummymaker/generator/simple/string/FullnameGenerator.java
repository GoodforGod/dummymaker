package io.dummymaker.generator.simple.string;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;


/**
 * Generates fullname
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class FullnameGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("person|candidate|fullname|fio", CASE_INSENSITIVE);

    private final NameGenerator nameGenerator = new NameGenerator();
    private final MiddleNameGenerator middleNameGenerator = new MiddleNameGenerator();
    private final SurnameGenerator surnameGenerator = new SurnameGenerator();

    @Override
    public @NotNull String generate() {
        return nameGenerator + " " + middleNameGenerator + " " + surnameGenerator;
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -10;
    }
}
