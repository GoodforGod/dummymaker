package io.dummymaker.cases;

import org.jetbrains.annotations.NotNull;

/**
 * All words are in low case: Bob - bob, TOnY - tony
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class LowCase implements Case {

    @Override
    public @NotNull String apply(@NotNull String value) {
        return value.toLowerCase();
    }
}
