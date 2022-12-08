package io.dummymaker.cases;

import org.jetbrains.annotations.NotNull;

/**
 * First letter is low case, next letters are as is: Bobby - bobby, TonNy - tonNy
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class CamelCase implements Case {

    @Override
    public @NotNull String apply(@NotNull String value) {
        return (value.length() == 1)
                ? value.toLowerCase()
                : value.substring(0, 1).toLowerCase() + value.substring(1);
    }
}
