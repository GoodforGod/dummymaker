package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates status from one of lists
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.12.2021
 */
public final class StatusGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("status|type", CASE_INSENSITIVE);

    private static final List<String> TYPES_RUSSIAN = Arrays.asList("успех", "ошибка", "отклоненный", "неверно");
    private static final List<String> TYPES_ENGLISH = Arrays.asList("success", "failed", "rejected", "invalid");

    @Override
    public String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(get(parameters.localisation())).toString();
    }

    @Override
    public String get() {
        return get(Localisation.ENGLISH);
    }

    private static String get(@NotNull Localisation localisation) {
        if (localisation == Localisation.RUSSIAN) {
            return CollectionUtils.random(TYPES_RUSSIAN);
        }
        return CollectionUtils.random(TYPES_ENGLISH);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
