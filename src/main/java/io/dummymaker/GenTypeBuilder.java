package io.dummymaker;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public interface GenTypeBuilder {

    /**
     * @param type to generate
     * @return type instance with generated fields
     * @param <T> erasure
     */
    @Nullable
    <T> T build(@NotNull Class<T> type);
}
