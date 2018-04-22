package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.generator.simple.IGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Generates random string like "aag2151tgdsfa9352tf"
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class StringGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        final String result = UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");

        final List<String> letters = Arrays.asList(result.split(""));
        Collections.shuffle(letters);

        final StringBuilder shuffled = new StringBuilder();
        for (String letter : letters)
            shuffled.append(letter);

        return shuffled.toString();
    }
}
