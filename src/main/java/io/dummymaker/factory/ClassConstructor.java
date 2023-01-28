package io.dummymaker.factory;

import io.dummymaker.error.GenConstructionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
interface ClassConstructor {

    @Nullable
    <T> T instantiate(@NotNull Class<T> target) throws GenConstructionException;
}
