package io.dummymaker.factory.refactored;

import java.util.List;
import java.util.Objects;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 24.11.2022
 */
public final class GenPayload {

    private final GenType type;
    private final int depth;
    private final List<GenContainer> fields;

    public GenPayload(GenType type, int depth, List<GenContainer> fields) {
        this.type = type;
        this.depth = depth;
        this.fields = fields;
    }

    public GenType getType() {
        return type;
    }

    public int getDepth() {
        return depth;
    }

    public List<GenContainer> getFields() {
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GenPayload payload = (GenPayload) o;
        return Objects.equals(type, payload.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
