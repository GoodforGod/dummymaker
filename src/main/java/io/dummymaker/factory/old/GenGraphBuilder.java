package io.dummymaker.factory.old;

import static io.dummymaker.util.CastUtils.isUnknownComplex;

import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import io.dummymaker.model.Node;
import io.dummymaker.model.Payload;
import io.dummymaker.scan.GenAutoScanner;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Builds embedded gen auto depth graph for storage
 *
 * @author Anton Kurako (GoodforGod)
 * @see MainGenStorage
 * @since 12.08.2019
 */
final class GenGraphBuilder {

    private final GenRules rules;
    private final GenAutoScanner scanner;

    GenGraphBuilder(GenAutoScanner scanner, GenRules rules) {
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

        scanner.scan(parentType, true).stream()
                .filter(e -> e.isEmbedded() || e.isComplex())
                .filter(e -> isUnknownComplex(e.getField().getType()))
                .map(e -> buildPayload(e.getField().getType(), parentPayload))
                .map(p -> Node.of(p, parent))
                .map(this::scanRecursively)
                .forEach(parent::add);

        return parent;
    }

    private Predicate<Node> buildFilter(Node child) {
        return n -> n.parent().value().equals(child.value());
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
                .map(rule -> rule.targeted(target)
                        .filter(GenRule::isAuto)
                        .map(GenRule::getDepth)
                        .orElse(-1))
                .filter(d -> d != -1)
                .orElse(null);

        final Optional<Integer> optionalDepth = Optional.ofNullable(autoDepth);
        final Integer markedOrDefault = optionalDepth.orElseGet(() -> parentPayload == null
                ? 1
                : parentPayload.getDepth());

        return new Payload(target, markedOrDefault, optionalDepth.isPresent());
    }

    /**
     * Checks by predicate whenever node is safe to add
     * <p>
     * Node is safe to add as child if such link is not presented as for parent -> child -> parent This
     * is done to avoid recursive
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
        for (Node n : node.nodes()) {
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
        return (node.parent() == null)
                ? node
                : findRoot(node.parent());
    }
}
