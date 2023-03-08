package io.dummymaker;

import io.dummymaker.annotation.GenDepth;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

/**
 * Builds embedded gen auto depth graph for storage
 *
 * @author Anton Kurako (GoodforGod)
 * @since 16.11.2022
 */
final class GenGraphBuilder {

    private final GenRules rules;
    private final GenScanner scanner;
    private final int depthByDefault;

    GenGraphBuilder(GenScanner scanner, GenRules rules, int depthByDefault) {
        this.scanner = scanner;
        this.rules = rules;
        this.depthByDefault = depthByDefault;
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
        final GenNode root = GenNode.ofRoot(payload);
        return scanRecursively(root);
    }

    /**
     * Scan target class and its embedded fields for gen containers
     *
     * @param parent to scan
     * @return parent node with all children
     */
    private GenNode scanRecursively(GenNode parent) {
        final List<GenType> flatten = parent.value().type().flatten();
        for (GenType type : flatten) {
            final List<GenNode> nodes = scanner.scan(type).stream()
                    .filter(container -> container.isEmbedded() || container.isComplex())
                    .flatMap(container -> {
                        final List<GenType> flattenTypes = container.type().flatten();
                        return Stream.concat(flattenTypes.stream(), Stream.of(container.type()));
                    })
                    .distinct()
                    .map(flatType -> buildPayload(flatType, parent.value()))
                    .map(payload -> GenNode.of(payload, parent))
                    .collect(Collectors.toList());

            final Set<GenNode> nodesToScan = new HashSet<>();
            for (GenNode node : nodes) {
                final Optional<GenNode> alreadyInGraph = find(parent, buildFilter(node.value().type()));
                if (alreadyInGraph.isPresent()) {
                    parent.add(alreadyInGraph.get());
                } else {
                    parent.add(node);
                    nodesToScan.add(node);
                }
            }

            for (GenNode node : nodesToScan) {
                final GenNode genNode = scanRecursively(node);
                parent.add(genNode);
            }
        }

        return parent;
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
        Class<?> plain = target.raw();
        if (plain.getTypeName().endsWith("[][]")) {
            plain = plain.getComponentType().getComponentType();
        } else if (plain.getTypeName().endsWith("[]")) {
            plain = plain.getComponentType();
        }

        final Class<?> targetType = plain;
        final Optional<GenRule> rule = rules.find(targetType);
        final int depth = rule.flatMap(GenRule::getDepth)
                .orElseGet(() -> Arrays.stream(targetType.getDeclaredAnnotations())
                        .filter(a -> a instanceof GenDepth)
                        .map(a -> ((GenDepth) a).value())
                        .findAny()
                        .orElseGet(() -> parentPayload == null
                                ? depthByDefault
                                : parentPayload.depth()));

        final List<GenContainer> containers = scanner.scan(SimpleGenType.ofClass(targetType));
        return new GenPayload(target, depth, containers);
    }

    private Optional<GenNode> find(GenNode node, Predicate<GenNode> filter) {
        final GenNode root = node.root();

        final Set<GenNode> visited = new HashSet<>();
        if (node != root) {
            visited.add(root);
            if (filter.test(root)) {
                return Optional.of(root);
            }
        }

        return find(root, filter, visited);
    }

    /**
     * Checks recursively all graph for predicate match
     *
     * @param rootNode graph start point
     * @param filter   to check against
     * @param visited  nodes to avoid recursion
     * @return whenever such linkage exists
     */
    private Optional<GenNode> find(GenNode rootNode, Predicate<GenNode> filter, Set<GenNode> visited) {
        GenNode result;

        for (GenNode node : rootNode.nodes()) {
            if (visited.add(node)) {
                if (filter.test(node)) {
                    return Optional.of(node);
                } else {
                    result = find(node, filter, visited).orElse(null);
                    if (result != null) {
                        return Optional.of(result);
                    }
                }
            }
        }

        return Optional.empty();
    }

    private Predicate<GenNode> buildFilter(GenType childType) {
        return node -> node.value().type().equals(childType);
    }
}
