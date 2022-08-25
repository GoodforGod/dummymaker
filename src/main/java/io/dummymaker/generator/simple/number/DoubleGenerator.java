package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.IGenerator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates double from 0 to 1
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class DoubleGenerator implements IGenerator<Double> {

    private final Pattern pattern = Pattern.compile("probability|chance|odds|expectation|possibility", CASE_INSENSITIVE);

    @Override
    public @NotNull Double generate() {
        return ThreadLocalRandom.current().nextDouble();
    }

    @Override
    public Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -40;
    }
}
