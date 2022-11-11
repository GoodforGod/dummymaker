package io.dummymaker.scan;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

/**
 * List based scanner contract
 *
 * @author Anton Kurako (GoodforGod)
 * @since 25.07.2019
 */
public interface Scanner<V, T> {

    /**
     * Simple scanner contract
     *
     * @param target to scan
     * @return list with values
     */
    @NotNull
    Collection<V> scan(T target);
}
