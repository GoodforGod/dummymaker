package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.generator.simple.IGenerator;

import java.util.UUID;

/**
 * Generates random string 36 character length
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class IdBigGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        return UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");
    }
}
