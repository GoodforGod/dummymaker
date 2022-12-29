package io.dummymaker.factory.refactored;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public interface TypeBuilder {

    @Nullable
    <T> T build(@NotNull Class<T> type);
}
