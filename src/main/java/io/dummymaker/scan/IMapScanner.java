package io.dummymaker.scan;

import java.util.Map;

/**
 * Map based scanner contract
 *
 * @author GoodforGod
 * @since 06.07.2017
 */
public interface IMapScanner<K, V, T> {

    /**
     * Map based scanner contract
     *
     * @param target to scan
     * @return map as scanner result
     */
    Map<K, V> scan(T target);
}
