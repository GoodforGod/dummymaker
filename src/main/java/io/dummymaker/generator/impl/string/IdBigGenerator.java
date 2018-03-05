package io.dummymaker.generator.impl.string;

import io.dummymaker.generator.IGenerator;

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
