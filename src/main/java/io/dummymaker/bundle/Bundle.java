package io.dummymaker.bundle;

import org.jetbrains.annotations.NotNull;

/**
 * Provides default bundles of values for generators
 *
 * @author Anton Kurako (GoodforGod)
 * @since 31.05.2017
 */
public interface Bundle {

    /**
     * Get bundle values by index
     *
     * @param index index in bundle collection
     * @return bundle value
     */
    String get(int index);

    /**
     * Get random bundle value
     *
     * @return bundle value
     */
    @NotNull
    String random();

    /**
     * Get bundle capacity
     *
     * @return bundle collections capacity
     */
    int size();
}
