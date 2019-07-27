package io.dummymaker.generator.simple.impl.string;


import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates password as a string, from 6 to 31 length
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class PassGenerator extends IdBigGenerator {

    private final Pattern pattern = Pattern.compile("pass(word)?", CASE_INSENSITIVE);

    @Override
    public String generate() {
        int passLength = ThreadLocalRandom.current().nextInt(6, 31);
        return super.generate().substring(0, passLength);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
