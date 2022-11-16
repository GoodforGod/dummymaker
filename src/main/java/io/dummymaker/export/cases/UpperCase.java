package io.dummymaker.export.cases;

import io.dummymaker.export.Case;
import org.jetbrains.annotations.NotNull;

/**
 * All words are in upper case: look - LOOK
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class UpperCase implements Case {

    @Override
    public @NotNull String apply(@NotNull String value) {
        return value.toUpperCase();
    }
}
