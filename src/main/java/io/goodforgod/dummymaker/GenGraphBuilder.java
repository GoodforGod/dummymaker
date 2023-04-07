package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.annotation.GenDepth;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builds embedded gen auto depth graph for storage
 *
 * @author Anton Kurako (GoodforGod)
 * @since 16.11.2022
 */
final class GenGraphBuilder {

    private final GenRulesContext rules;
    private final GenConstructorScanner constructorScanner;
    private final GenFieldScanner fieldScanner;
    private final int depthByDefault;

    GenGraphBuilder(GenConstructorScanner constructorScanner,
                    GenFieldScanner fieldScanner,
                    GenRulesContext rules,
                    int depthByDefault) {
        this.constructorScanner = constructorScanner;
        this.fieldScanner = fieldScanner;
        this.rules = rules;
        this.depthByDefault = depthByDefault;
    }

    /**
     * Scan target class and builds graph for auto depth values
     *
     * @param target to build graph of
     * @return graph of auto depth values
     */
    GenNode build(@NotNull Class<?> target) {
        final GenType type = DefaultGenType.ofClass(target);
        final Optional<GenRuleContext> rule = rules.findClass(target);
        final int depth = rule.flatMap(GenRuleContext::getDepth)
                .orElseGet(() -> Arrays.stream(target.getDeclaredAnnotations())
                        .filter(a -> a instanceof GenDepth)
                        .map(a -> ((GenDepth) a).value())
                        .findAny()
                        .orElse(depthByDefault));

        final GenClass payload = buildPayload(type, null, depth);
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
        final List<GenType> flattenTypes = parent.value().type().flatten().stream()
                .filter(type -> fieldScanner.isEmbedded(parent.value().type(), type))
                .collect(Collectors.toList());

        for (GenType flatType : flattenTypes) {
            final List<GenNode> nodes = fieldScanner.scan(flatType).stream()
                    .filter(field -> field.isEmbedded() || field.isComplex())
                    .flatMap(field -> {
                        final List<GenType> fieldFlattenTypes = field.type().flatten();
                        return Stream.concat(fieldFlattenTypes.stream(), Stream.of(field.type()))
                                .distinct()
                                .filter(type -> fieldScanner.isEmbedded(flatType, type))
                                .map(type -> buildPayload(type, parent.value(), field.depth().orElse(null)));
                    })
                    .map(payload -> GenNode.of(payload, parent))
                    .collect(Collectors.toList());

            final Set<GenNode> nodesToScan = new HashSet<>();
            for (GenNode node : nodes) {
                final Optional<GenNode> nodeAlreadyInGraph = find(parent, buildFilter(node.value().type()));
                if (nodeAlreadyInGraph.isPresent()) {
                    parent.add(nodeAlreadyInGraph.get());
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

        final List<GenParameter> parameters = parent.value().constructor().parameters().stream()
                .filter(parameter -> fieldScanner.isEmbedded(parent.value().type(), parameter.type()))
                .collect(Collectors.toList());

        for (GenParameter parameter : parameters) {
            final Set<GenNode> nodesToScan = new HashSet<>();
            for (GenType flatParameterType : parameter.type().flatten()) {
                if (fieldScanner.isEmbedded(parent.value().type(), flatParameterType)) {
                    final GenClass payload = buildPayload(flatParameterType, parent.value(), parent.value().depth());
                    final GenNode node = GenNode.of(payload, parent);

                    final Optional<GenNode> nodeAlreadyInGraph = find(parent, buildFilter(node.value().type()));
                    if (nodeAlreadyInGraph.isPresent()) {
                        parent.add(nodeAlreadyInGraph.get());
                    } else {
                        parent.add(node);
                        nodesToScan.add(node);
                    }
                }
            }

            for (GenNode node : nodesToScan) {
                final GenNode genNode = scanRecursively(node);
                parent.add(genNode);
            }
        }

        return parent;
    }

    private GenClass buildPayload(GenType target,
                                  @Nullable GenClass parentPayload,
                                  @Nullable Integer depth) {
        Class<?> raw = target.raw();
        if (raw.getTypeName().endsWith("[][]")) {
            raw = raw.getComponentType().getComponentType();
        } else if (raw.getTypeName().endsWith("[]")) {
            raw = raw.getComponentType();
        }

        final int payloadDepth = Optional.ofNullable(depth)
                .orElseGet(() -> parentPayload == null
                        ? depthByDefault
                        : parentPayload.depth());

        final GenType type = DefaultGenType.ofClass(raw);
        final List<GenField> fields = fieldScanner.scan(type);
        final GenConstructor constructor = constructorScanner.scan(type);
        return new GenClass(target, payloadDepth, constructor, fields);
    }

    private Optional<GenNode> find(GenNode node, Predicate<GenNode> filter) {
        final GenNode root = node.root();

        final Set<GenNode> visited = new HashSet<>();
        if (node == root) {
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
