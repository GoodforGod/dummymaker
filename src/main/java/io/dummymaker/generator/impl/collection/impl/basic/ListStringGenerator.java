package io.dummymaker.generator.impl.collection.impl.basic;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.string.StringGenerator;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates list of strings
 *
 * @see StringGenerator
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class ListStringGenerator implements IGenerator<List<String>> {

    private final IGenerator<String> generator = new StringGenerator();

    @Override
    public List<String> generate() {
        final List<String> strings = new ArrayList<>();
        final int amount = current().nextInt(1,10);

        for(int i = 0; i < amount; i++)
            strings.add(generator.generate());

        return strings;
    }
}
