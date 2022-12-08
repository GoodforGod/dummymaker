package io.dummymaker.cases;

import org.jetbrains.annotations.NotNull;

/**
 * First letter is upper case, next letters are as is: Bobby - Bobby, tonNy - TonNy
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.04.2018
 */
public final class PascalCase implements Case {

    @Override
    public @NotNull String apply(@NotNull String value) {
        return (value.length() == 1)
                ? value.toUpperCase()
                : value.substring(0, 1).toUpperCase() + value.substring(1);
    }
}
