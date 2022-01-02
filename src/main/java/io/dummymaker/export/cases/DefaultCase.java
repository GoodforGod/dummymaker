package io.dummymaker.export.cases;

import io.dummymaker.export.ICase;
import org.jetbrains.annotations.NotNull;

/**
 * Returns default value as is.
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class DefaultCase implements ICase {

    @Override
    public @NotNull String format(@NotNull String value) {
        return value;
    }
}
