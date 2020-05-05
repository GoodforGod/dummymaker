package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Generates random string 36 character length
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class IdBigGenerator implements IGenerator<String> {

    @Override
    public @NotNull String generate() {
        return UUID.randomUUID().toString() + UUID.randomUUID().toString();
    }
}
