package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Cadastral number generator
 *
 * @author GoodforGod
 * @since 12.10.2019
 */
public class CadastralGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("cadastral|cad(num)?", CASE_INSENSITIVE);

    @Override
    public @NotNull String generate() {
        return String.format("%s:%s:%s:%s",
                CollectionUtils.random(10, 99),
                CollectionUtils.random(10, 99),
                CollectionUtils.random(100000, 9999999),
                CollectionUtils.random(10, 99));
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
