package io.dummymaker.factory.refactored;

import java.util.List;
import java.util.Objects;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 24.11.2022
 */
final class GenPayload {

    private final GenType type;
    private final int depth;
    private final List<GenContainer> fields;

    GenPayload(GenType type, int depth, List<GenContainer> fields) {
        this.type = type;
        this.depth = depth;
        this.fields = fields;
    }

    GenType type() {
        return type;
    }

    int depth() {
        return depth;
    }

    List<GenContainer> fields() {
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenPayload)) return false;
        GenPayload that = (GenPayload) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
