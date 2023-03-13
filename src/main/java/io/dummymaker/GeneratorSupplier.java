package io.dummymaker;

import io.dummymaker.generator.Generator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 20.12.2022
 */
interface GeneratorSupplier {

    @NotNull
    Generator<?> get(@NotNull Class<?> type);

    @NotNull
    Generator<?> get(@NotNull Class<?> type, @NotNull String fieldName);
}
