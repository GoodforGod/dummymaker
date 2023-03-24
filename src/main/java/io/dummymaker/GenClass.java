package io.dummymaker;

import java.util.List;
import java.util.Objects;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 24.11.2022
 */
final class GenClass {

    private final boolean embedded;
    private final int depth;
    private final GenType type;
    private final List<GenField> fields;
    private GenConstructor constructor;

    GenClass(boolean embedded, GenType type, int depth, List<GenField> fields) {
        this.embedded = embedded;
        this.type = type;
        this.depth = depth;
        this.fields = fields;
    }

    boolean isEmbedded() {
        return embedded;
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
        if (constructor == null) {
            constructor = new GenConstructor(type());
        }
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
