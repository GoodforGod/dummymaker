package io.dummymaker;

import java.util.List;
import java.util.Objects;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 24.11.2022
 */
final class GenClass {

    private final int depth;
    private final GenType type;
    private final GenConstructor constructor;
    private final List<GenField> fields;

    GenClass(GenType type, int depth, GenConstructor constructor, List<GenField> fields) {
        this.type = type;
        this.depth = depth;
        this.fields = fields;
        this.constructor = constructor;
    }

    int depth() {
        return depth;
    }

    GenType type() {
        return type;
    }

    List<GenField> fields() {
        return fields;
    }

    GenConstructor constructor() {
        return constructor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenClass))
            return false;
        GenClass that = (GenClass) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
