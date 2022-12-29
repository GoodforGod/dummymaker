package io.dummymaker.factory.refactored;

import io.dummymaker.annotation.GenDepth;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.dummymaker.util.CastUtils.isUnknownComplex;

/**
 * Builds embedded gen auto depth graph for storage
 *
 * @author Anton Kurako (GoodforGod)
 * @since 16.11.2022
 */
final class GenGraphBuilder {

    private final GenRules rules;
    private final GenScanner scanner;

    GenGraphBuilder(GenScanner scanner, GenRules rules) {
        this.scanner = scanner;
        this.rules = rules;
    }

    /**
     * Scan target class and builds graph for auto depth values
     *
     * @param target to build graph of
     * @return graph of auto depth values
     */
    GenNode build(Class<?> target) {
        final GenType type = SimpleGenType.ofClass(target);
        final GenPayload payload = buildPayload(type, null);
        final GenNode node = GenNode.of(payload, null);
        return scanRecursively(node);
    }

    /**
     * Scan target class and its embedded fields for gen containers
     *
     * @param parent to scan
     * @return parent node with all children
     */
    private GenNode scanRecursively(GenNode parent) {
        final GenPayload parentPayload = parent.value();
        final GenType parentType = parentPayload.getType();

        if (!isSafe(parent, buildFilter(parent))) {
            return parent;
        }

        final List<GenNode> nodes = scanner.scan(parentType).stream()
                .filter(container -> container.isEmbedded() || container.isComplex())
                .filter(container -> isUnknownComplex(container.getField().getType()))
                .map(container -> buildPayload(container.getType(), parentPayload))
                .map(payload -> GenNode.of(payload, parent))
                .collect(Collectors.toList());

        for (GenNode node : nodes) {
            parent.add(node);
            GenNode genNode = scanRecursively(node);
            parent.add(genNode);
        }

        return parent;
    }

    private Predicate<GenNode> buildFilter(GenNode child) {
        return n -> n.parent().value().equals(child.value());
    }

    /**
     * Builds payload for target class
     *
     * @param target        to scan
     * @param parentPayload parent payload
     * @return payload for target class
     */
    private GenPayload buildPayload(GenType target, @Nullable GenPayload parentPayload) {
        // First check rules for auto depth then check annotation if present
        final Class<?> targetType = target.value();
        final Optional<GenRule> rule = rules.find(targetType);
        final Integer autoDepth = rule.flatMap(GenRule::getDepth)
                .orElse(Arrays.stream(targetType.getDeclaredAnnotations())
                        .filter(a -> a instanceof GenDepth)
                        .map(a -> ((GenDepth) a).value())
                        .findAny()
                        .orElse(null));

        final boolean isAuto = autoDepth == null;
        final int depthMarkedOrDefault = isAuto || parentPayload == null
                ? 1
                : parentPayload.getDepth();

        final List<GenContainer> containers = scanner.scan(target);
        return new GenPayload(target, depthMarkedOrDefault, containers);
    }

    /**
     * Checks by predicate whenever node is safe to add
     * <p>
     * Node is safe to add as child if such link is not presented as for parent -> child -> parent This
     * is done to avoid recursive
     *
     * @param node   as graph starting point
     * @param filter check against
     * @return whenever it is safe to add node as child
     */
    private boolean isSafe(GenNode node, Predicate<GenNode> filter) {
        final GenNode root = findRoot(node);
        return !find(root, filter).isPresent();
    }

    /**
     * Checks recursively all graph for predicate match
     *
     * @param rootNode   graph start point
     * @param filter to check against
     * @return whenever such linkage exists
     */
    private Optional<GenNode> find(GenNode rootNode, Predicate<GenNode> filter) {
        GenNode result;
        for (GenNode node : rootNode.nodes()) {
            if (filter.test(node)) {
                return Optional.of(node);
            } else {
                result = find(node, filter).orElse(null);
                if (result != null) {
                    return Optional.of(result);
                }
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
    private GenNode findRoot(GenNode node) {
        return (node.parent() == null)
                ? node
                : findRoot(node.parent());
    }
}
