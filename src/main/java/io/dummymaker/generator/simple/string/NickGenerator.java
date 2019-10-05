package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.impl.NicknamesBundle;
import io.dummymaker.generator.IGenerator;

import java.util.regex.Pattern;

import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates nicknames as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 05.06.2017
 */
public class NickGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("nick(name)?|login", CASE_INSENSITIVE);

    private final NicknamesBundle bundle = new NicknamesBundle();

    @Override
    public String generate() {
        boolean revert = current().nextBoolean();
        boolean tuple = current().nextBoolean();

        final String first = bundle.getRandom();
        final String second = bundle.getRandom();

        return (!tuple)
                ? first
                : (revert) ? second + first
                : first + second;
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
