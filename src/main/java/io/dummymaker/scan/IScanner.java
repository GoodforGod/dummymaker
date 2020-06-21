package io.dummymaker.scan;

import org.jetbrains.annotations.NotNull;

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
    @NotNull
    Collection<V> scan(T target);
}
