package io.dummymaker.generator.simple.string;


import io.dummymaker.generator.IGenerator;
import java.util.UUID;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;


/**
 * Generates id based on UUID
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class IdGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("[UuGg]?[Uu]?[Ii]d$|^[UuGg]?[Uu]?[Ii]d");

    @Override
    public @NotNull String generate() {
        return UUID.randomUUID().toString();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -100;
    }
}
