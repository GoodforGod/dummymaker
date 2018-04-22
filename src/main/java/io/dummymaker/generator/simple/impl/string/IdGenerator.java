package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.generator.simple.IGenerator;

import java.util.UUID;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class IdGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
