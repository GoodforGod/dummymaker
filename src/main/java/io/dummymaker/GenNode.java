package io.dummymaker;

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

    public static GenNode ofRoot(GenClass value) {
        return new GenNode(value, null, true);
    }

    public static GenNode of(GenClass value, GenNode parent) {
        return new GenNode(value, parent, false);
    }

    public GenNode add(GenNode child) {
        this.nodes.add(child);
        return this;
    }

    public GenClass value() {
        return value;
    }

    public GenNode parent() {
        return parent;
    }

    public Set<GenNode> nodes() {
        return nodes;
    }

    public GenNode root() {
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
            return "Root Value: " + value.type();
        } else {
            return "Value: " + value.type();
        }
    }
}
