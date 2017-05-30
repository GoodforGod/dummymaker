package io.generator;

import io.generator.populate.GenPopulator;
import io.generator.populate.IPopulator;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class EntityGenFactory<T> implements IGenFactory<T>, IPopulator<T> {

    private final GenPopulator<T> populateFactory = new GenPopulator<>();

    private Class<T> primeClass;

    public EntityGenFactory(Class<T> primeClass) {
        this.primeClass = primeClass;
    }

    @Override
    public T populate(T t) {
        return populateFactory.populate(t);
    }

    @Override
    public T produce() {
        T instance = null;

        try {
            instance = primeClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    @Override
    public List<T> produce(int amount) {

        return null;
    }
}
