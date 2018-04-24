package io.dummymaker.container.impl;

import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;

import java.util.List;

import static io.dummymaker.util.BasicCollectionUtils.getRandomIndex;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 24.04.2018
 */
public class GeneratorsContainer {

    private final List<IGenerator> generators;
    private final List<IComplexGenerator> complexGenerators;

    public GeneratorsContainer(final List<IGenerator> generators,
                               final List<IComplexGenerator> complexGenerators) {
        this.generators = generators;
        this.complexGenerators = complexGenerators;
    }

    public IGenerator getGeneratorRandom() {
        return this.generators.get(getRandomIndex(generators));
    }

    public IComplexGenerator getComplexGeneratorRandom() {
        return this.complexGenerators.get(getRandomIndex(complexGenerators));
    }

    public List<IGenerator> getGenerators() {
        return generators;
    }

    public List<IComplexGenerator> getComplexGenerators() {
        return complexGenerators;
    }
}
