package io.dummymaker.factory.old;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.generator.Generator;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Config that providers contract for generators discovery Based on fields and classes for auto
 * generation
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenAuto
 * @since 27.07.2019
 */
@Deprecated
public interface GenSupplier {

    /**
     * Finds suitable generator for field from all available generators
     *
     * @param field target
     * @return suitable generator
     */
    @NotNull
    Class<? extends Generator> getSuitable(@NotNull Field field);

    /**
     * Finds suitable generator for field from all available generators
     *
     * @param field target
     * @param type  target that can sometimes differ from field type Like parent class, or
     *              implementation not interface
     * @return suitable generator
     */
    @NotNull
    Class<? extends Generator> getSuitable(@NotNull Field field, @Nullable Class<?> type);
}
