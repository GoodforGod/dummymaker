package io.dummymaker.cases;

import org.jetbrains.annotations.NotNull;

/**
 * Returns default value as is.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class DefaultCase implements NamingCase {

    DefaultCase() {}

    @Override
    public @NotNull CharSequence apply(@NotNull CharSequence value) {
        return value;
    }
}
