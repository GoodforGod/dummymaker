package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.util.List;

/**
 * Generates random string from values
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.01.2023
 */
public final class StringValuesGenerator implements Generator<CharSequence> {

    private final List<String> values;

    public StringValuesGenerator(List<String> values) {
        this.values = values;
    }

    @Override
    public String get() {
        return CollectionUtils.random(values);
    }
}
