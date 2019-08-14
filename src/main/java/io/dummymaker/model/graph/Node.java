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
public class Node<T> {

    private T value;

    private Node<T> parent;
    private Set<Node<T>> nodes = new HashSet<>();

    private Node(T value, Node<T> parent) {
        this.value = value;
        this.parent = parent;
    }

    public static <T> Node<T> of(T value, Node<T> parent) {
        final Node<T> node = new Node<>(value, parent);
        if(parent != null)
            parent.add(node);

        return node;
    }

    public Node<T> add(Node<T> child) {
        this.nodes.add(child);
        return this;
    }

    public T value() {
        return value;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Set<Node<T>> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(value, node.value) &&
                Objects.equals(parent, node.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, parent);
    }
}