package io.dummymaker.generate;

import java.util.UUID;

/**
 * Generates random string 36 character length
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class StringGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
