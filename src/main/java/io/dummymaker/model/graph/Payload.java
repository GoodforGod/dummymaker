package io.dummymaker.model.graph;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 05.08.2019
 */
public class Payload {

    private final Class<?> type;
    private final int depth;

    public Payload(Class<?> type, int depth) {
        this.type = type;
        this.depth = depth;
    }

    public Class<?> getType() {
        return type;
    }

    public int getDepth() {
        return depth;
    }
}
