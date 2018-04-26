package io.dummymaker.container.impl;

import io.dummymaker.generator.simple.IGenerator;

import java.util.HashMap;
import java.util.Map;

import static io.dummymaker.util.BasicCastUtils.instantiate;

/**
 * Stores generators instances thought populate factory life cycle
 *
 * @see io.dummymaker.factory.IPopulateFactory
 *
 * @author GoodforGod
 * @since 25.04.2018
 */
public class GeneratorsStorage {

    private final Map<Class<? extends IGenerator>, IGenerator> generators = new HashMap<>();

    public IGenerator getGeneratorInstance(final Class<? extends IGenerator> generatorClass) {
        final IGenerator generator = generators.get(generatorClass);
        if(generator != null)
            return generator;

        final IGenerator iGenerator = instantiate(generatorClass);
        generators.put(generatorClass, iGenerator);
        return iGenerator;
    }
}
