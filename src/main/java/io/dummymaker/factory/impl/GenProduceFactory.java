package io.dummymaker.factory.impl;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.factory.IProduceFactory;

import java.util.ArrayList;
import java.util.Collections;
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
public class GenProduceFactory implements IProduceFactory {

    private static final Logger logger = Logger.getLogger(GenProduceFactory.class.getName());

    private static final IPopulateFactory populateFactory = new GenPopulateFactory();

    @Override
    public <T> T produce(Class<T> tClass) {
        try {
            return populateFactory.populate(tClass.newInstance());
        } catch (InstantiationException e) {
            logger.warning(e.getMessage() + " | OBJECT MIGHT NOT HAVE ZERO PUBLIC CONSTRUCTOR! CAN NOT INSTANTIATE NEW OBJECT!");
        }
        catch (IllegalAccessException e) {
            logger.warning(e.getMessage() + " | DOES NOT HAVE ACCESS TO OBJECT TO INSTANTIATE IT!");
        }
        return null;
    }

    @Override
    public <T> List<T> produce(Class<T> tClass, final int amount) {
        if(amount < 1)
            return Collections.emptyList();

        try {
            final List<T> produced = new ArrayList<>();
            for(int i = 0; i < amount; i++)
                produced.add(tClass.newInstance());
            return populateFactory.populate(produced);

        } catch (InstantiationException | IllegalAccessException e) {
            logger.warning(e.getMessage() + " | OBJECT MIGHT NOT HAVE ZERO PUBLIC CONSTRUCTOR! CAN NOT INSTANTIATE OBJECT!");
            return Collections.emptyList();
        }
    }
}
