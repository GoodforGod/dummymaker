package io.dummymaker.generator;

import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public interface Ordered extends Comparable<Ordered> {

    default int order() {
        return 0;
    }

    @Override
    default int compareTo(@NotNull Ordered o) {
        return Integer.compare(order(), o.order());
    }
}
