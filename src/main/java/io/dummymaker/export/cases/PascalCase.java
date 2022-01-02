package io.dummymaker.export.cases;

import io.dummymaker.export.ICase;
import org.jetbrains.annotations.NotNull;

/**
 * First letter is upper case, next letters are as is: Bobby - Bobby, tonNy - TonNy
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public class PascalCase implements ICase {

    @Override
    public @NotNull String format(@NotNull String value) {
        return (value.length() == 1)
                ? value.toUpperCase()
                : value.substring(0, 1).toUpperCase() + value.substring(1);
    }
}
