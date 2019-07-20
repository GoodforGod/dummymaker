package io.dummymaker.factory.impl;

import io.dummymaker.annotation.special.GenSequential;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.container.impl.GeneratorsStorage;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.SequentialGenerator;
import io.dummymaker.scan.IPopulateScanner;
import io.dummymaker.scan.impl.SequentialScanner;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 17.07.2019
 */
class GenFactoryStorage {

    private final GeneratorsStorage generatorsStorage;
    private final Map<Field, IGenerator> sequentialGenerators;
    private final Map<Field, GenContainer> containerMap;
    private final Set<Field> nullables;

    GenFactoryStorage(Class<?> targetClass,
                      IPopulateScanner populateScanner)  {
        this.containerMap = populateScanner.scan(targetClass);
        this.sequentialGenerators = scanForSequentialFields(targetClass);
        this.nullables = new HashSet<>();
        this.generatorsStorage = new GeneratorsStorage();
    }

    public IGenerator getGenerator(Class<? extends IGenerator> generatorClass) {
        return generatorsStorage.getGenInstance(generatorClass);
    }

    /**
     * Full scanned containers to field map
     * @return gen container map to field
     */
    Map<Field, GenContainer> getContainers() {
        return containerMap;
    }

    /**
     * Containers without nullable fields
     * @return gen container map to field
     */
    Map<Field, GenContainer> getFilteredContainers() {
        return containerMap.entrySet().stream()
                .filter(e -> !isNullable(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public boolean isSequential(Field field) {
        return sequentialGenerators.containsKey(field);
    }

    public boolean isNullable(Field field) {
        return nullables.contains(field);
    }

    public Field markNullable(Field field) {
        nullables.add(field);
        return field;
    }

    private Map<Field, IGenerator> scanForSequentialFields(Class<?> target) {
        return new SequentialScanner().scan(target)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new SequentialGenerator(((GenSequential) e.getValue().get(0)).from()))
                );
    }
}
