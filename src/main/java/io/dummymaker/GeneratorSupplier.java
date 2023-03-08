package io.dummymaker;

import io.dummymaker.generator.Generator;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 20.12.2022
 */
interface GeneratorSupplier {

    @NotNull
    Generator<?> get(@NotNull Class<?> type);

    @NotNull
    Generator<?> get(@NotNull Field field);
}
