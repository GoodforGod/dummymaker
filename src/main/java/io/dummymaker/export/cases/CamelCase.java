package io.dummymaker.export.cases;

import io.dummymaker.export.ICase;
import org.jetbrains.annotations.NotNull;

/**
 * First letter is low case, next letters are as is: Bobby - bobby, TonNy -
 * tonNy
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class CamelCase implements ICase {

    @Override
    public @NotNull String format(@NotNull String value) {
        return (value.length() == 1)
                ? value.toLowerCase()
                : value.substring(0, 1).toLowerCase() + value.substring(1);
    }
}
