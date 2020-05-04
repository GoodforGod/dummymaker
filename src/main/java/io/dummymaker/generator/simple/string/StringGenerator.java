package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;

import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates random string like "aag2151tgdsfa9352tf"
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class StringGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("[a-zA-Z]|strs?", CASE_INSENSITIVE);

    @Override
    public String generate() {
        return UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");
    }
}
