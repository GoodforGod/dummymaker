package io.dummymaker.scan;

import org.jetbrains.annotations.NotNull;

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
    @NotNull
    Map<K, V> scan(T target);
}
