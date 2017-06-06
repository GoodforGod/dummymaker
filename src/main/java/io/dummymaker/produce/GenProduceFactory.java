package io.dummymaker.produce;

import io.dummymaker.populate.GenPopulateFactory;
import io.dummymaker.populate.IPopulateFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Produce Dummy Objects and populate them via PrimeGenAnnotation generators included
 *
 * @see io.dummymaker.annotation.prime.PrimeGenAnnotation
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class GenProduceFactory<T> implements IProduceFactory<T> {

    private final Logger logger = Logger.getLogger(GenProduceFactory.class.getName());

    private final IPopulateFactory<T> populateFactory = new GenPopulateFactory<>();

    private final Class<T> primeClass;

    public GenProduceFactory(Class<T> primeClass) {
        this.primeClass = primeClass;
    }

    @Override
    public T produce() {
        T instance = null;

        try {
            instance = populateFactory.populate(primeClass.newInstance());
        } catch (Exception e) {
            logger.warning(e.getMessage() + " | OBJECT MIGHT NOT HAVE ZERO PUBLIC CONSTRUCTOR! CAN NOT PRODUCE INSTANTIATE!");
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
