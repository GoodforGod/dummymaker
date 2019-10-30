package io.dummymaker.model.graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 05.08.2019
 */
public class Node {

    private Payload value;

    private Node parent;
    private Set<Node> nodes = new HashSet<>();

    private Node(Payload value, Node parent) {
        this.value = value;
        this.parent = parent;
    }

    public static Node of(Payload value, Node parent) {
        final Node node = new Node(value, parent);
        if (parent != null)
            parent.add(node);

        return node;
    }

    public Node add(Node child) {
        this.nodes.add(child);
        return this;
    }

    public Payload value() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Objects.equals(value, node.value) &&
                Objects.equals(parent, node.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, parent);
    }
}
