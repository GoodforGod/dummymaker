package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.PhoneCodeBundle;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.Localisation;
import io.dummymaker.util.RandomUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates simple mobile phone as a string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class PhoneGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("telefax|phone|mobile(phone)?|dial", CASE_INSENSITIVE);
    private static final Bundle PHONE_CODE_BUNDLE = new PhoneCodeBundle();

    private final boolean formatted;

    public PhoneGenerator(boolean formatted) {
        this.formatted = formatted;
    }

    @Override
    public @NotNull String get() {
        final int first = RandomUtils.random(100, 999);
        final int second = RandomUtils.random(100, 999);
        final int third = RandomUtils.random(1000, 9999);
        String prefix = PHONE_CODE_BUNDLE.random(Localisation.DEFAULT);

        if (formatted) {
            return "+" + prefix + first + second + third;
        }

        prefix = (ThreadLocalRandom.current().nextBoolean())
                ? "+" + prefix
                : prefix;

        switch (RandomUtils.random(0, 5)) {
            case 0:
                return prefix + " " + first + " " + second + " " + third;
            case 1:
                return prefix + "-" + first + "-" + second + "-" + third;
            case 2:
                return prefix + "(" + first + ")" + second + third;
            case 3:
                return prefix + "." + first + "." + second + "." + third;
            default:
                return prefix + first + second + third;
        }
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -65;
    }
}
