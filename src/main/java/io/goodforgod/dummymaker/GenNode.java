package io.goodforgod.dummymaker;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 24.11.2022
 */
final class GenNode {

    private final GenNode parent;
    private final Set<GenNode> nodes = new HashSet<>();
    private final GenClass value;
    private final boolean isRoot;

    private GenNode(GenClass value, GenNode parent, boolean isRoot) {
        this.value = value;
        this.parent = parent;
        this.isRoot = isRoot;
    }

    static GenNode ofRoot(GenClass value) {
        return new GenNode(value, null, true);
    }

    static GenNode of(GenClass value, GenNode parent) {
        return new GenNode(value, parent, false);
    }

    GenNode add(GenNode child) {
        this.nodes.add(child);
        return this;
    }

    GenClass value() {
        return value;
    }

    GenNode parent() {
        return parent;
    }

    Set<GenNode> nodes() {
        return nodes;
    }

    GenNode root() {
        return (isRoot)
                ? this
                : parent.root();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenNode))
            return false;
        GenNode genNode = (GenNode) o;
        return Objects.equals(value, genNode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        if (parent == null) {
            return "[root=" + value.type() + "]";
        } else {
            return "[value=" + value.type() + "]";
        }
    }
}
