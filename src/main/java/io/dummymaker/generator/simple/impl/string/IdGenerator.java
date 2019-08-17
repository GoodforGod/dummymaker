package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.generator.simple.IGenerator;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Generates id based on UUID
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class IdGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("[ug]?u?id|(.*Id.*)");

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
