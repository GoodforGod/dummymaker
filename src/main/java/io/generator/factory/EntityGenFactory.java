package io.generator.factory;

import io.generator.populate.GenPopulator;
import io.generator.populate.IPopulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class EntityGenFactory<T> implements IGenFactory<T>, IPopulator<T> {

    private final IPopulator<T> populateFactory = new GenPopulator<>();

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
            instance = populate(primeClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    @Override
    public List<T> produce(int amount) {
        List<T> produced = new ArrayList<>();

        int realAmount = (amount > 0) ? amount : 0;

        while (realAmount > 0) {
            T t = produce();

            if(t != null)
                produced.add(t);

            realAmount--;
        }

        return produced;
    }
}
