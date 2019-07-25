package io.dummymaker.scan;

import java.util.Collection;

/**
 * List based scanner contract
 *
 * @author GoodforGod
 * @since 25.07.2019
 */
public interface IScanner<V, T> {

    /**
     * Simple scanner contract
     *
     * @param target to scan
     * @return list with values
     */
    Collection<V> scan(T target);
}
