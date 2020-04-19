package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates type as one of adjectives ladders level of 'Good, Bad'
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.4.2020
 */
public class TypeGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("type|adjective|level|lvl", CASE_INSENSITIVE);

    private final List<String> types = Arrays.asList("terrible", "bad", "poor", "limited", "neutral", "average", "Decent", "fine",
            "superior");

    @Override
    public String generate() {
        return types.get(ThreadLocalRandom.current().nextInt(types.size() - 1));
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
