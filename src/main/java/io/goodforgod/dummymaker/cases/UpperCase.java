package io.goodforgod.dummymaker.cases;

import org.jetbrains.annotations.NotNull;

/**
 * All words are in upper case: look - LOOK
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
final class UpperCase implements NamingCase {

    UpperCase() {}

    @Override
    public @NotNull CharSequence apply(@NotNull CharSequence value) {
        return value.toString().toUpperCase();
    }
}
