package io.dummymaker.factory.impl;

import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.model.graph.Node;
import io.dummymaker.model.graph.Payload;
import io.dummymaker.scan.IPopulateScanner;
import io.dummymaker.scan.impl.PopulateScanner;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Builds embedded gen auto depth graph for storage
 *
 * @author GoodforGod
 * @see GenStorage
 * @since 12.08.2019
 */
class GenGraphBuilder {

    private final IPopulateScanner scanner;

    GenGraphBuilder(IPopulateScanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Scan target class and builds graph for auto depth values
     *
     * @param target to build graph of
     * @return graph of auto depth values
     */
    Node<Payload> build(Class<?> target) {
        final Payload payload = buildPayload(target, null);
        final Node<Payload> node = Node.of(payload, null);
        return scanRecursively(node);
    }

    /**
     * Scan target class and its embedded fields for gen containers
     *
     * @param parent to scan
     * @return parent node with all children
     */
    private Node<Payload> scanRecursively(Node<Payload> parent) {
        final Payload parentPayload = parent.value();
        final Class<?> parentType = parentPayload.getType();

        scanner.scan(parentType).entrySet().stream()
                .filter(e -> e.getValue().isEmbedded())
                .map(e -> buildPayload(e.getKey().getType(), parentPayload))
                .map(p -> Node.of(p, parent))
                .filter(child -> isSafe(child, n -> n.value().equals(parent.value()) && n.getParent().value().equals(child.value())))
                .map(this::scanRecursively)
                .forEach(parent::add);

        return parent;
    }

    /**
     * Builds payload for target class
     *
     * @param target        to scan
     * @param parentPayload parent payload
     * @return payload for target class
     */
    private Payload buildPayload(Class<?> target, Payload parentPayload) {
        final int defaultDepth = (parentPayload != null) ? parentPayload.getDepth() : 1;
        final int depth = PopulateScanner.getAutoAnnotation(target)
                .map(a -> ((GenAuto) a).depth()).orElse(defaultDepth);

        return new Payload(target, depth);
    }

    /**
     * Checks by predicate whenever node is safe to add
     * <p>
     * Node is safe to add as child if such link is not presented as for parent -> child -> parent
     * This is done to avoid recursive
     *
     * @param node   as graph starting point
     * @param filter check against
     * @param <T>    payload type
     * @return whenever its safe to add node as child
     */
    private <T> boolean isSafe(Node<T> node, Predicate<Node<T>> filter) {
        final Node<T> root = findRoot(node);
        return !find(root, filter).isPresent();
    }

    /**
     * Checks recursively all graph for predicate match
     *
     * @param node   graph start point
     * @param filter to check against
     * @param <T>    payload type
     * @return whenever such linkage exists
     */
    <T> Optional<Node<T>> find(Node<T> node, Predicate<Node<T>> filter) {
        Node<T> result = null;
        for (Node<T> n : node.getNodes()) {
            if (filter.test(n))
                return Optional.of(n);
            else
                result = find(n, filter).orElse(null);
        }

        return Optional.ofNullable(result);
    }

    /**
     * Finds graph root
     *
     * @param node graph starting point
     * @return graph root
     */
    @SuppressWarnings("unchecked")
    private <T> Node<T> findRoot(Node<T> node) {
        return (node.getParent() != null)
                ? findRoot(node.getParent())
                : node;
    }
}