package io.dummymaker.scan;

import java.util.Map;

/**
 * Base scanner contract
 *
 * @author GoodforGod (Anton Kurako)
 * @since 06.07.2017
 */
public interface IScanner<K, V> {

    /**
     * Base class scanner contract
     *
     * @param t class to scan
     * @return Returns Map with KEY and VALUES associated with annotated class
     */
    Map<K, V> scan(final Class t);
}
