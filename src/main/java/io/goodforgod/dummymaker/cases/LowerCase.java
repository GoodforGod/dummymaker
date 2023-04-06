package io.goodforgod.dummymaker.cases;

import org.jetbrains.annotations.NotNull;

/**
 * All words are in low case: Bob - bob, TOnY - tony
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
final class LowerCase implements NamingCase {

    LowerCase() {}

    @Override
    public @NotNull CharSequence apply(@NotNull CharSequence value) {
        return value.toString().toLowerCase();
    }
}
