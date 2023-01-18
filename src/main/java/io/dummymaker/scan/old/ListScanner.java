package io.dummymaker.scan.old;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * List based scanner contract
 *
 * @author Anton Kurako (GoodforGod)
 * @since 25.07.2019
 */
@Deprecated
public interface ListScanner<V, T> {

    /**
     * Simple scanner contract
     *
     * @param target to scan
     * @return list with values
     */
    @NotNull
    List<V> scan(T target);
}
