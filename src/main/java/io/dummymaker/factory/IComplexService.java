package io.dummymaker.factory;

import io.dummymaker.generator.simple.IGenerator;

/**
 * Used to extend complex generator functionality
 *
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @author GoodforGod
 * @since 21.07.2019
 */
public interface IComplexService {

    <T> T fillWithDepth(T t, int depth);

    IGenerator getGenerator(Class<? extends IGenerator> generatorClass);
}
