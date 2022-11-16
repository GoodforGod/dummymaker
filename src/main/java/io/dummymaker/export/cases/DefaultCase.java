package io.dummymaker.export.cases;

import io.dummymaker.export.Case;
import org.jetbrains.annotations.NotNull;

/**
 * Returns default value as is.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class DefaultCase implements Case {

    @Override
    public @NotNull String apply(@NotNull String value) {
        return value;
    }
}
