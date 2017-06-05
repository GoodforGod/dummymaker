package io.dummymaker;

import io.dummymaker.populate.GenPopulateFactory;
import io.dummymaker.populate.IPopulateFactory;
import io.dummymaker.produce.GenProduceFactory;
import io.dummymaker.produce.IProduceFactory;

import java.util.List;

/**
 * Prime factory implementation
 *
 * @see IPrimeFactory
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class GenPrimeFactory<T> implements IPrimeFactory<T>{

    private final IProduceFactory<T> produceFactory;
    private final IPopulateFactory<T> populateFactory;

    public GenPrimeFactory(Class<T> primeClass) {
        produceFactory = new GenProduceFactory<>(primeClass);
        populateFactory = new GenPopulateFactory<>();
    }

    @Override
    public T produce() {
        return produceFactory.produce();
    }

    @Override
    public List<T> produce(int amount) {
        return produceFactory.produce(amount);
    }

    @Override
    public T populate(T t) {
        return populateFactory.populate(t);
    }

    @Override
    public List<T> populate(List<T> t) {
        return populateFactory.populate(t);
    }
}
