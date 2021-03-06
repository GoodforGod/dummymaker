package io.dummymaker.model.graph;

import java.util.Objects;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 05.08.2019
 */
public class Payload {

    private final Class<?> type;
    private final int depth;
    private final boolean isMarkedAuto;

    public Payload(Class<?> type, int depth, boolean isMarkedAuto) {
        this.type = type;
        this.depth = depth;
        this.isMarkedAuto = isMarkedAuto;
    }

    public Class<?> getType() {
        return type;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isMarkedAuto() {
        return isMarkedAuto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Payload payload = (Payload) o;
        return Objects.equals(type, payload.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
