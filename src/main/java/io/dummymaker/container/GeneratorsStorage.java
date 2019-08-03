package io.dummymaker.container;

import io.dummymaker.generator.simple.IGenerator;

import java.util.HashMap;
import java.util.Map;

import static io.dummymaker.util.CastUtils.instantiate;

/**
 * Stores generators instances thought populate factory life cycle
 *
 * @author GoodforGod
 * @see io.dummymaker.factory.IPopulateFactory
 * @since 25.04.2018
 */
public class GeneratorsStorage {

    private final Map<Class<? extends IGenerator>, IGenerator> generators = new HashMap<>();

    public IGenerator getGenInstance(final Class<? extends IGenerator> generatorClass) {
        return generators.computeIfAbsent(generatorClass, (k) -> instantiate(generatorClass));
    }
}
