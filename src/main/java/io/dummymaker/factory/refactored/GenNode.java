package io.dummymaker.factory.refactored;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 24.11.2022
 */
public final class GenNode {

    private final GenNode parent;
    private final Set<GenNode> nodes = new HashSet<>();
    private final GenPayload value;

    private GenNode(GenPayload value, GenNode parent) {
        this.value = value;
        this.parent = parent;
    }

    public static GenNode of(GenPayload value, GenNode parent) {
        final GenNode node = new GenNode(value, parent);
        if (parent != null)
            parent.add(node);

        return node;
    }

    public GenNode add(GenNode child) {
        this.nodes.add(child);
        return this;
    }

    public GenPayload value() {
        return value;
    }

    public GenNode parent() {
        return parent;
    }

    public Set<GenNode> nodes() {
        return nodes;
    }

    public GenNode root() {
        return (parent() == null)
                ? this
                : parent.root();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenNode))
            return false;
        GenNode node = (GenNode) o;
        return Objects.equals(value, node.value) &&
                Objects.equals(parent, node.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, parent);
    }

    @Override
    public String toString() {
        if (parent == null) {
            return "[value: " + value.getType() + "]";
        } else {
            return "[parent: " + parent.value.getType() + ", value: " + value.getType() + "]";
        }
    }
}
