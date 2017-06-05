package io.dummymaker;

import io.dummymaker.populate.IPopulateFactory;
import io.dummymaker.produce.IProduceFactory;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public interface IPrimeFactory<T> extends IPopulateFactory<T>, IProduceFactory<T> {

}
