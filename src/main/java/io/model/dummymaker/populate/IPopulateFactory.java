package io.model.dummymaker.populate;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
public interface IPopulateFactory<T> {
    T populate(T t);

    List<T> populate(List<T> t);
}
