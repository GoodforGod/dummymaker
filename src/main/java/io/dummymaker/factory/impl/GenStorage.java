package io.dummymaker.factory.impl;

import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.annotation.special.GenSequence;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.factory.IGenSupplier;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.generator.simple.impl.SequenceGenerator;
import io.dummymaker.model.GenContainer;
import io.dummymaker.model.graph.Node;
import io.dummymaker.model.graph.Payload;
import io.dummymaker.scan.IPopulateScanner;
import io.dummymaker.scan.impl.PopulateScanner;
import io.dummymaker.scan.impl.SequenceScanner;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.dummymaker.util.CastUtils.instantiate;

/**
 * Storage that facilitates generator storage, scanners, field mapping and nullable fields
 * To improve factory performance and extend complex generators abilities
 *
 * @author GoodforGod
 * @since 17.07.2019
 */
class GenStorage implements IGenStorage {

    private final IGenSupplier supplier;
    private final GenFactory embeddedFactory; // stupid? yes, have better solution pls PR
    private final IPopulateScanner scanner;

    private final Map<Class<? extends IGenerator>, IGenerator> generators;
    private final Map<Class<?>, Map<Field, IGenerator>> sequentialGenerators;
    private final Map<Class<?>, Map<Field, GenContainer>> containers;
    private final Set<Field> marked;

    private Node<Payload> graph;

    GenStorage(IPopulateScanner scanner) {
        this.scanner = scanner;

        this.embeddedFactory = new GenEmbeddedFactory(scanner);
        this.supplier = new GenSupplier();

        this.sequentialGenerators = new HashMap<>();
        this.containers = new HashMap<>();
        this.generators = new HashMap<>();
        this.marked = new HashSet<>();
    }

    private Node<Payload> buildGraph(Class<?> target) {
        final Payload payload = buildPayload(target, null);
        final Node<Payload> node = Node.as(payload, null);
        return scanRecursively(node);
    }

    /**
     * Scan target class and its embedded fields for gen containers
     *
     * @param parent to scan
     * @return
     */
    private Node<Payload> scanRecursively(Node<Payload> parent) {
        final Payload parentPayload = parent.getValue();
        final Class<?> type = parentPayload.getType();
        final Map<Field, GenContainer> parentFields = scanner.scan(type);


        for (Map.Entry<Field, GenContainer> entry : parentFields.entrySet()) {
            if (entry.getValue().isEmbedded()) {
                final Class<?> childTarget = entry.getKey().getType();
                final Payload payload = buildPayload(childTarget, parentPayload);
                final Node<Payload> childBase = Node.as(payload, parent);

                final Predicate<Node<Payload>> filter = (n) -> (n.getParent() == null || parent.getValue().equals(n.getParent().getValue()))
                        && n.getValue().equals(childBase.getValue());

                final Predicate<Node<Payload>> filter2 = n -> n.getValue().equals(childBase.getValue()) && n.getParent().getValue().equals(parent.getValue());

                final Predicate<Node<Payload>> filter3 = n -> n.getValue().equals(parent.getValue()) && n.getParent().getValue().equals(childBase.getValue());
                parent.add(childBase);

                if (isSafe(childBase, filter3)) {
                    final Node<Payload> child = scanRecursively(childBase);
                    parent.add(child);
                }
            }
        }

        return parent;
    }

    private Payload buildPayload(Class<?> target, Payload parentPayload) {
        final int defaultDepth = (parentPayload != null) ? parentPayload.getDepth() : 1;
        final int depth = PopulateScanner.getAutoAnnotation(target)
                .map(a -> ((GenAuto) a).depth()).orElse(defaultDepth);

        return new Payload(target, depth);
    }

    private <T> boolean isSafe(Node<T> node, Predicate<Node<T>> filter) {
        final Node<T> root = findRoot(node);
        return !isPayloadExist(root, filter);
    }

    private <T> boolean isPayloadExist(Node<T> node, Predicate<Node<T>> filter) {
        boolean result = false;
        for (Node<T> n : node.getNodes()) {
            if (filter.test(n))
                return true;
            else
                result |= isPayloadExist(n, filter);
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private <T> Node<T> findRoot(Node<T> node) {
        if (node.getParent() == null)
            return node;

        return findRoot(node.getParent());
    }

    @Override
    public IGenerator getGenerator(Class<? extends IGenerator> generatorClass) {
        return (generatorClass == null)
                ? generators.computeIfAbsent(NullGenerator.class, (k) -> instantiate(NullGenerator.class))
                : generators.computeIfAbsent(generatorClass, (k) -> instantiate(generatorClass));
    }

    @Override
    public Class<? extends IGenerator> getSuitable(Field field) {
        return supplier.getSuitable(field);
    }

    @Override
    public Class<? extends IGenerator> getSuitable(Field field, Class<?> type) {
        return supplier.getSuitable(field, type);
    }

    /**
     * @param t     entity
     * @param depth to start entity data fill with
     * @param <T>   type
     * @return entity filled with data
     * @see GenEmbeddedFactory#fillWithDepth(Object, int, GenStorage)
     */
    @Override
    public <T> T fillByDepth(T t, int depth) {
        return embeddedFactory.fillEntity(t, this, depth);
    }

    /**
     * Full scanned containers to field map
     *
     * @return gen container map to field
     */
    <T> Map<Field, GenContainer> getContainers(T t) {
        if (t == null)
            return Collections.emptyMap();

        if (this.graph == null) {
            this.graph = buildGraph(t.getClass());
            System.out.println(graph);
        }

        final Class<?> targetClass = t.getClass();
        scanForSequentialFields(targetClass);
        return containers.computeIfAbsent(targetClass, (k) -> scanner.scan(targetClass));
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
    IGenerator getSequential(Class<?> target, Field field) {
        return sequentialGenerators.computeIfAbsent(target, (k) -> {
            final Map<Field, IGenerator> map = new HashMap<>();
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
    private void scanForSequentialFields(Class<?> target) {
        this.sequentialGenerators.computeIfAbsent(target, (k) -> new SequenceScanner().scan(target)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new SequenceGenerator(((GenSequence) e.getValue().get(0)).from()))
                ));
    }
}
