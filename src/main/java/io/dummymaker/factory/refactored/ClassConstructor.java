package io.dummymaker.factory.refactored;

import io.dummymaker.error.ClassConstructorException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public interface ClassConstructor {

    @Nullable
    <T> T instantiate(@NotNull Class<T> target) throws ClassConstructorException;
}
