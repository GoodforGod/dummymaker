package io.dummymaker;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.03.2023
 */
final class GenParameter {

    private final GenType type;
    private final String name;

    GenParameter(GenType type, String name) {
        this.type = type;
        this.name = name;
    }

    GenType type() {
        return type;
    }

    String name() {
        return name;
    }
}
