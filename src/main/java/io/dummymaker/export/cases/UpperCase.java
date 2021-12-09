package io.dummymaker.export.cases;


import io.dummymaker.export.ICase;
import org.jetbrains.annotations.NotNull;


/**
 * All words are in upper case: look - LOOK
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class UpperCase implements ICase {

    @Override
    public @NotNull String format(@NotNull String value) {
        return value.toUpperCase();
    }
}
