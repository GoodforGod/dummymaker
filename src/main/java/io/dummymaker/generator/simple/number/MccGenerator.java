package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.MccBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.10.2022
 */
public class MccGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("mcc", CASE_INSENSITIVE);
    private final IBundle bundle = new MccBundle();

    @Override
    public Integer generate() {
        return Integer.valueOf(bundle.random());
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
