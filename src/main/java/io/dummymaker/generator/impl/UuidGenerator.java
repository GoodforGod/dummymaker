package io.dummymaker.generator.impl;

import io.dummymaker.generator.IGenerator;

import java.util.UUID;

/**
 * Generates UUIDs
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class UuidGenerator implements IGenerator<UUID> {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}
