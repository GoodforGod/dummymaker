package io.dummymaker.factory.impl;

import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import io.dummymaker.model.graph.Node;
import io.dummymaker.model.graph.Payload;
import io.dummymaker.scan.IPopulateAutoScanner;
import io.dummymaker.scan.impl.PopulateAutoScanner;

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

    private final GenRules rules;
    private final IPopulateAutoScanner scanner;

    GenGraphBuilder(IPopulateAutoScanner scanner, GenRules rules) {
        this.scanner = scanner;
        this.rules = rules;
    }

    /**
     * Scan target class and builds graph for auto depth values
     *
     * @param target to build graph of
     * @return graph of auto depth values
     */
    Node build(Class<?> target) {
        final Payload payload = buildPayload(target, null);
        final Node node = Node.of(payload, null);
        return scanRecursively(node);
    }

    /**
     * Scan target class and its embedded fields for gen containers
     *
     * @param parent to scan
     * @return parent node with all children
     */
    private Node scanRecursively(Node parent) {
        final Payload parentPayload = parent.value();
        final Class<?> parentType = parentPayload.getType();

        if (!isSafe(parent, buildFilter(parent)))
            return parent;

        scanner.scan(parentType, true).entrySet().stream()
                .filter(e -> e.getValue().isEmbedded())
                .map(e -> buildPayload(e.getKey().getType(), parentPayload))
                .map(p -> Node.of(p, parent))
                .map(this::scanRecursively)
                .forEach(parent::add);

        return parent;
    }

    private Predicate<Node> buildFilter(Node child) {
        return n -> n.getParent().value().equals(child.value());
    }

    /**
     * Builds payload for target class
     *
     * @param target        to scan
     * @param parentPayload parent payload
     * @return payload for target class
     */
    private Payload buildPayload(Class<?> target, Payload parentPayload) {
        // First check rules for auto depth then check annotation if present
        final Integer autoDepth = Optional.ofNullable(rules)
                .map(r -> r.targeted(target)
                        .filter(GenRule::isAuto)
                        .map(GenRule::getDepth)
                        .orElse(-1))
                .filter(d -> d != -1)
                .orElse(PopulateAutoScanner.getAutoAnnotation(target)
                        .map(a -> ((GenAuto) a).depth())
                        .orElse(null));

        final Optional<Integer> optionalDepth = Optional.ofNullable(autoDepth);

        final Integer markedOrDefault = optionalDepth.orElseGet(() -> parentPayload == null ? 1 : parentPayload.getDepth());
        return new Payload(target, markedOrDefault, optionalDepth.isPresent());
    }

    /**
     * Checks by predicate whenever node is safe to add
     * <p>
     * Node is safe to add as child if such link is not presented as for parent -> child -> parent
     * This is done to avoid recursive
     *
     * @param node   as graph starting point
     * @param filter check against
     * @return whenever its safe to add node as child
     */
    private boolean isSafe(Node node, Predicate<Node> filter) {
        final Node root = findRoot(node);
        return !find(root, filter).isPresent();
    }

    /**
     * Checks recursively all graph for predicate match
     *
     * @param node   graph start point
     * @param filter to check against
     * @return whenever such linkage exists
     */
    Optional<Node> find(Node node, Predicate<Node> filter) {
        Node result;
        for (Node n : node.getNodes()) {
            if (filter.test(n)) {
                return Optional.of(n);
            } else {
                result = find(n, filter).orElse(null);
                if (result != null)
                    return Optional.of(result);
            }
        }

        return Optional.empty();
    }

    /**
     * Finds graph root
     *
     * @param node graph starting point
     * @return graph root
     */
    private Node findRoot(Node node) {
        return (node.getParent() == null)
                ? node
                : findRoot(node.getParent());
    }
}
