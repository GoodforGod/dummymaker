package io.dummymaker;

import io.dummymaker.generator.Generator;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.03.2023
 */
final class GenParameter {

    private final GenType type;
    private final String name;
    private final Generator<?> generator;

    GenParameter(GenType type, String name, Generator<?> generator) {
        this.type = type;
        this.name = name;
        this.generator = generator;
    }

    GenType type() {
        return type;
    }

    String name() {
        return name;
    }

    Generator<?> generator() {
        return generator;
    }
}
