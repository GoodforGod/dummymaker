package io.dummymaker.factory.impl;

import io.dummymaker.container.impl.GeneratorsContainer;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.DoubleGenerator;
import io.dummymaker.util.BasicCastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 24.04.2018
 */
public class AutoGeneratorsFactory {

    private final Map<Class, GeneratorsContainer> classGeneratorsMap;

    public AutoGeneratorsFactory() {
        this.classGeneratorsMap = new HashMap<>();
        Class<? extends IGenerator> cg = DoubleGenerator.class;
        String s = ((Class<?>) BasicCastUtils.getGenericType(cg.getGenericInterfaces()[0])).getTypeName();
    }

    private GeneratorsContainer buildStringGenerators() {
        final List<IGenerator> generators               = new ArrayList<>();
        final List<IComplexGenerator> complexGenerators = new ArrayList<>();

        return new GeneratorsContainer(generators, complexGenerators);
    }

    private GeneratorsContainer buildIntGenerators() {
        final List<IGenerator> generators               = new ArrayList<>();
        final List<IComplexGenerator> complexGenerators = new ArrayList<>();

        return new GeneratorsContainer(generators, complexGenerators);
    }

    private GeneratorsContainer buildLongGenerators() {
        final List<IGenerator> generators               = new ArrayList<>();
        final List<IComplexGenerator> complexGenerators = new ArrayList<>();

        return new GeneratorsContainer(generators, complexGenerators);
    }

    private GeneratorsContainer buildDoubleGenerators() {
        final List<IGenerator> generators               = new ArrayList<>();
        final List<IComplexGenerator> complexGenerators = new ArrayList<>();

        return new GeneratorsContainer(generators, complexGenerators);
    }

    private GeneratorsContainer buildGenerators() {
        final List<IGenerator> generators               = new ArrayList<>();
        final List<IComplexGenerator> complexGenerators = new ArrayList<>();

        return new GeneratorsContainer(generators, complexGenerators);
    }
}
