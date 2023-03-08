package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.GenParameters;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates type as one of adjectives ladders level of 'Good, Bad'
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.4.2020
 */
public final class TypeGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("adjective|intense|difficult|level|lvl", CASE_INSENSITIVE);

    private static final List<String> TYPES_RUSSIAN = Arrays.asList("Ужасный", "Плохой", "Удручающий", "Не очень",
            "Нейтральный", "Средний", "Посредственный", "Хороший", "Супер");

    private static final List<String> TYPES_ENGLISH = Arrays.asList("Terrible", "Bad", "Poor", "Limited",
            "Neutral", "Average", "Decent", "Fine", "Superior");

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
