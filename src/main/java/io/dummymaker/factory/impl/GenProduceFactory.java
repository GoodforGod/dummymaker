package io.dummymaker.factory.impl;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.factory.IProduceFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.dummymaker.util.BasicCastUtils.instantiate;

/**
 * Produce Dummy Objects and populate them via PrimeGen generators included
 *
 * @author GoodforGod
 * @see PrimeGen
 * @since 26.05.2017
 */
public class GenProduceFactory implements IProduceFactory {

    private final IPopulateFactory populateFactory;

    public GenProduceFactory() {
        this.populateFactory = new GenPopulateFactory();
    }

    @Override
    public <T> T produce(final Class<T> tClass) {
        return populateFactory.populate(instantiate(tClass));
    }

    @Override
    public <T> List<T> produce(final Class<T> tClass, final int amount) {
        if (amount < 1 || instantiate(tClass) == null)
            return Collections.emptyList();

        final List<T> produced = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++)
            produced.add(instantiate(tClass));

        return populateFactory.populate(produced);
    }
}
