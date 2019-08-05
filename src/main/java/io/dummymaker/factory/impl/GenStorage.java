package io.dummymaker.factory.impl;

import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.annotation.special.GenSequence;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.factory.IGenSupplier;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.generator.simple.impl.SequenceGenerator;
import io.dummymaker.model.GenContainer;
import io.dummymaker.scan.IPopulateScanner;
import io.dummymaker.scan.impl.PopulateScanner;
import io.dummymaker.scan.impl.SequenceScanner;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
    private final GenEmbeddedFactory simpleFactory;
    private final IPopulateScanner populateScanner;

    private final Map<Class<? extends IGenerator>, IGenerator> generators;
    private final Map<Class<?>, Map<Field, IGenerator>> sequentialGenerators;
    private final Map<Class<?>, Map<Field, GenContainer>> containerMap;
    private final Set<Field> marked;

    GenStorage(IPopulateScanner populateScanner) {
        this.populateScanner = populateScanner;

        this.simpleFactory = new GenEmbeddedFactory(populateScanner);
        this.supplier = new GenSupplier();

        this.sequentialGenerators = new HashMap<>();
        this.containerMap = new HashMap<>();
        this.generators = new HashMap<>();
        this.marked = new HashSet<>();
    }

    GenStorage(IPopulateScanner populateScanner, Class<?> target) {
        this(populateScanner);

        final int depth = PopulateScanner.getAutoAnnotation(target)
                .map(a -> ((GenAuto) a).depth()).orElse(1);
        scanRecursively(target, depth);
    }

    /**
     * Scan target class and its embedded fields for gen containers
     *
     * @param target to scan
     */
    private void scanRecursively(Class<?> target, int parentDepth) {
        final Map<Field, GenContainer> scannedTarget = populateScanner.scan(target);
        final int depth = PopulateScanner.getAutoAnnotation(target)
                .map(a -> ((GenAuto) a).depth()).orElse(parentDepth);

        for (Map.Entry<Field, GenContainer> entry : scannedTarget.entrySet()) {
            entry.getValue().setAutoDepth(depth);
            if (entry.getValue().isEmbedded())
                scanRecursively(entry.getKey().getType(), depth);
        }

        containerMap.put(target, scannedTarget);
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
    public <T> T fillWithDepth(T t, int depth) {
        return simpleFactory.fillWithDepth(t, depth, this);
    }

    /**
     * Full scanned containers to field map
     *
     * @return gen container map to field
     */
    Map<Field, GenContainer> getContainers(Class<?> target) {
        if (target == null)
            return Collections.emptyMap();

        scanForSequentialFields(target);
        return containerMap.computeIfAbsent(target, (k) -> populateScanner.scan(target));
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
            final Map<Field, IGenerator> map = new ConcurrentHashMap<>();
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
