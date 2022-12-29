package io.dummymaker.factory.old;

import static io.dummymaker.util.CastUtils.instantiate;

import io.dummymaker.annotation.GenSequence;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.NullGenerator;
import io.dummymaker.generator.simple.SequenceGenerator;
import io.dummymaker.model.GenContainer;
import io.dummymaker.model.GenRules;
import io.dummymaker.model.Node;
import io.dummymaker.scan.GenAutoScanner;
import io.dummymaker.scan.impl.SequenceScanner;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Storage that facilitates generator storage, scanners, field mapping and nullable fields To
 * improve factory performance and extend complex generators abilities
 *
 * @author Anton Kurako (GoodforGod)
 * @since 17.07.2019
 */
final class MainGenStorage implements GenStorage {

    private final GenSupplier supplier;
    private final MainGenFactory embeddedFactory; // stupid? yes, have better solution pls PR
    private final GenAutoScanner scanner;
    private final GenGraphBuilder graphBuilder;

    private final Map<Class<? extends Generator>, Generator> generators;
    private final Map<Class<?>, Map<Field, Generator>> sequentialGenerators;
    private final Map<Class<?>, List<GenContainer>> containers;
    private final Set<Field> marked;

    private Node graph;

    MainGenStorage(GenAutoScanner scanner, GenRules rules) {
        this.scanner = scanner;

        this.embeddedFactory = new MainGenFactory(rules);
        this.graphBuilder = new GenGraphBuilder(scanner, rules);
        this.supplier = new MainGenSupplier();

        this.sequentialGenerators = new HashMap<>();
        this.containers = new HashMap<>();
        this.generators = new HashMap<>();
        this.marked = new HashSet<>();
    }

    @Override
    public @NotNull Generator<?> getGenerator(Class<? extends Generator> generatorClass) {
        return (generatorClass == null)
                ? generators.computeIfAbsent(NullGenerator.class, k -> instantiate(NullGenerator.class))
                : generators.computeIfAbsent(generatorClass, k -> instantiate(generatorClass));
    }

    @Override
    public @NotNull Class<? extends Generator> getSuitable(@NotNull Field field) {
        return supplier.getSuitable(field);
    }

    @Override
    public @NotNull Class<? extends Generator> getSuitable(@NotNull Field field, @Nullable Class<?> type) {
        return supplier.getSuitable(field, type);
    }

    @Override
    public <T> T fillByDepth(T t, int depth) {
        return embeddedFactory.fillEntity(t, this, depth);
    }

    @Override
    public int getDepth(Class<?> parent, Class<?> target) {
        if (target == null)
            return 1;

        final Predicate<Node> filter = n -> n.parent() != null
                && n.parent().value().getType().equals(parent)
                && n.value().getType().equals(target);

        return graphBuilder.find(graph, filter)
                .map(n -> n.parent().value().getDepth())
                .orElse(1);
    }

    /**
     * Full scanned containers to field map
     *
     * @return gen container map to field
     */
    List<GenContainer> getContainers(Object t) {
        if (t == null)
            return Collections.emptyList();

        final Class<?> target = t.getClass();

        // when encounters first object generation (always top level object)
        if (this.graph == null)
            this.graph = graphBuilder.build(target);

        markSequentialFields(target);

        final boolean isMarked = isAnyAutoMarked(target);
        return containers.computeIfAbsent(target, k -> scanner.scan(target, isMarked));
    }

    /**
     * Checks whenever any parent or node itself is marked as gen auto
     *
     * @param target to check
     * @return true if any parent or node itself is marked
     */
    private boolean isAnyAutoMarked(Class<?> target) {
        final Predicate<Node> filter = n -> n.value().getType().equals(target);
        if (filter.test(graph) && graph.value().isMarkedAuto())
            return true;

        final Optional<Node> node = graphBuilder.find(graph, filter);
        return node.filter(payloadNode -> isMarked(payloadNode, 1)).isPresent();
    }

    /**
     * Scan recursively if any parent or node is marked
     *
     * @param node  to scan
     * @param depth level to validate
     * @return true if parent or start node is marked
     * @see #isAnyAutoMarked(Class)
     */
    private boolean isMarked(Node node, int depth) {
        if (node.value().isMarkedAuto())
            return node.value().getDepth() >= depth;

        return node.parent() != null && isMarked(node.parent(), depth + 1);
    }

    /**
     * Was field marked with sequence generator
     *
     * @param target class
     * @param field  to check
     * @return is marked sequential
     */
    boolean isSequential(Class<?> target, Field field) {
        return sequentialGenerators.getOrDefault(target, Collections.emptyMap()).containsKey(field);
    }

    /**
     * Gets sequence generator for class field
     *
     * @param target class
     * @param field  to check
     * @return sequence generator
     */
    Generator getSequential(Class<?> target, Field field) {
        return sequentialGenerators.computeIfAbsent(target, k -> {
            final Map<Field, Generator> map = new HashMap<>(1);
            map.put(field, new SequenceGenerator());
            return map;
        }).get(field);
    }

    /**
     * Was field marked as nullable/invalid/etc
     *
     * @param field to check
     * @return was not marked or otherwise
     */
    boolean isUnmarked(Field field) {
        return !marked.contains(field);
    }

    /**
     * Mark field as nullable
     *
     * @param field to mark
     */
    void markNullable(Field field) {
        marked.add(field);
    }

    /**
     * Scan for sequence annotation marked fields
     *
     * @param target class to scan
     */
    private void markSequentialFields(Class<?> target) {
        this.sequentialGenerators.computeIfAbsent(target, k -> new SequenceScanner().scan(target)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new SequenceGenerator(((GenSequence) e.getValue().get(0)).from()))));
    }
}
