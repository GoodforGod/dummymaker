package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.generator.simple.IGenerator;

import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class IdGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("[ug]?u?id", CASE_INSENSITIVE);

    @Override
    public String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
