package io.dummymaker.cases;

import org.jetbrains.annotations.NotNull;

/**
 * Returns default value as is.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class DefaultCase implements Case {

    DefaultCase() {}

    @Override
    public @NotNull String apply(@NotNull String value) {
        return value;
    }
}
