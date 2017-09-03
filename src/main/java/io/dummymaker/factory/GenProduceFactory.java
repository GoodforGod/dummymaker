package io.dummymaker.factory;

import io.dummymaker.annotation.base.PrimeGenAnnotation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Produce Dummy Objects and populate them via PrimeGenAnnotation generators included
 *
 * @see PrimeGenAnnotation
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class GenProduceFactory<T> implements IProduceFactory<T> {

    private final Logger logger = Logger.getLogger(GenProduceFactory.class.getName());

    private final IPopulateFactory<T> populateFactory = new GenPopulateFactory<>();

    private final Class<T> produceClass;

    public GenProduceFactory(final Class<T> produceClass) {
        this.produceClass = produceClass;
    }

    @Override
    public T produce() {
        try {
            return populateFactory.populate(produceClass.newInstance());
        } catch (InstantiationException e) {
            logger.warning(e.getMessage() + " | OBJECT MIGHT NOT HAVE ZERO PUBLIC CONSTRUCTOR! CAN NOT INSTANTIATE NEW OBJECT!");
        }
        catch (IllegalAccessException e) {
            logger.warning(e.getMessage() + " | DOES NOT HAVE ACCESS TO OBJECT TO INSTANTIATE IT!");
        }
        return null;
    }

    @Override
    public List<T> produce(final int amount) {
        final List<T> produced = new ArrayList<>();

        if(amount < 1)
            return produced;

        try {
            for(int i = 0; i < amount; i++)
                produced.add(produceClass.newInstance());

        } catch (InstantiationException | IllegalAccessException e) {
            logger.warning(e.getMessage() + " | OBJECT MIGHT NOT HAVE ZERO PUBLIC CONSTRUCTOR! CAN NOT INSTANTIATE OBJECT!");
            return new ArrayList<>();
        }

        return populateFactory.populate(produced);
    }
}
