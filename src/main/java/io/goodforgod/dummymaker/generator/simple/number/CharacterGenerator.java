package io.goodforgod.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.util.CollectionUtils;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates random UTF-8 character in range of (97-122)
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.04.2018
 */
public final class CharacterGenerator implements ParameterizedGenerator<Character> {

    private static final Pattern PATTERN = Pattern.compile("letter|character", CASE_INSENSITIVE);

    private static final List<Character> LETTERS_RU = Arrays.asList(
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ч',
            'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я');

    @Override
    public Character get(@NotNull GenParameters parameters) {
        if (parameters.localisation() == Localisation.RUSSIAN) {
            final Character ruLetter = CollectionUtils.random(LETTERS_RU);
            if (parameters.namingCase() == NamingCases.DEFAULT) {
                return ThreadLocalRandom.current().nextBoolean()
                        ? Character.toUpperCase(ruLetter)
                        : ruLetter;
            } else {
                return parameters.namingCase().apply(ruLetter.toString()).charAt(0);
            }
        } else {
            return parameters.namingCase().apply(get().toString()).toString().charAt(0);
        }
    }

    @Override
    public @NotNull Character get() {
        boolean b = ThreadLocalRandom.current().nextBoolean();
        char c = (char) RandomUtils.random(97, 122);
        return (b)
                ? Character.toUpperCase(c)
                : c;
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
