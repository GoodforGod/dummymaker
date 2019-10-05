package io.dummymaker.factory.impl;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.scan.impl.GenAutoScanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.dummymaker.util.CastUtils.instantiate;

/**
 * Produce Dummy Objects and populate them via PrimeGen generators included
 *
 * @author GoodforGod
 * @see PrimeGen
 * @since 26.05.2017
 */
public class GenOldFactory extends PopulateFactory implements IProduceFactory {

    public GenOldFactory() {
        super(new GenAutoScanner(new GenSupplier()));
    }

    @Override
    public <T> T produce(final Class<T> tClass) {
        return populate(instantiate(tClass));
    }

    @Override
    public <T> List<T> produce(final Class<T> tClass, final int amount) {
        if (amount < 1 || instantiate(tClass) == null)
            return Collections.emptyList();

        final List<T> produced = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++)
            produced.add(instantiate(tClass));

        return populate(produced);
    }
}
