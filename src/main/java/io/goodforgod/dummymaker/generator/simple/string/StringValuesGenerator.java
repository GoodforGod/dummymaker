package io.goodforgod.dummymaker.generator.simple.string;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.CollectionUtils;
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
