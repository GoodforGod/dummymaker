package io.generator;

import io.generator.populate.IPopulateFactory;
import io.generator.produce.IProduceFactory;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public interface IPrimeFactory<T> extends IPopulateFactory<T>, IProduceFactory<T> {

}
