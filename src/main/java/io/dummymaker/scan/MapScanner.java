package io.dummymaker.scan;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Map based scanner contract
 *
 * @author Anton Kurako (GoodforGod)
 * @since 06.07.2017
 */
public interface MapScanner<K, V, T> {

    /**
     * Map based scanner contract
     *
     * @param target to scan
     * @return map as scanner result
     */
    @NotNull
    Map<K, V> scan(T target);
}
