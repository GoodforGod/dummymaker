package io.dummymaker.bundle;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public interface IBundle<T> {
    T get(int index);

    T getRandom();

    int size();
}
