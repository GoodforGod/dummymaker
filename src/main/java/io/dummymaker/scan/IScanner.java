package io.dummymaker.scan;

import java.util.Map;

/**
 * Scanner contract
 *
 * @author GoodforGod
 * @since 06.07.2017
 */
public interface IScanner<K, V, T> {

    /**
     * Base scanner contract
     *
     * @param target to scan
     * @return Returns Map with KEY and VALUES associated with annotated class
     */
    Map<K, V> scan(T target);
}
