package io.dummymaker.factory;

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

    private final Class<T> produceClass;

    public GenProduceFactory(Class<T> produceClass) {
        this.produceClass = produceClass;
    }

    @Override
    public T produce() {
        T instance = null;

        try {
            instance = populateFactory.populate(produceClass.newInstance());
        } catch (Exception e) {
            logger.warning(e.getMessage() + " | OBJECT MIGHT NOT HAVE ZERO PUBLIC CONSTRUCTOR! CAN NOT PRODUCE INSTANTIATE!");
        }

        return instance;
    }

    @Override
    public List<T> produce(int amount) {
        List<T> produced = new ArrayList<>();

        int realAmount = (amount > 0) ? amount : 0;

        try {
            for(int i = 0; i < realAmount; i++)
                produced.add(produceClass.newInstance());

        } catch (InstantiationException | IllegalAccessException e) {
            logger.warning(e.getMessage() + " | OBJECT MIGHT NOT HAVE ZERO PUBLIC CONSTRUCTOR! CAN NOT INSTANTIATE OBJECT!");
            return new ArrayList<>();
        }

        return populateFactory.populate(produced);
    }
}
