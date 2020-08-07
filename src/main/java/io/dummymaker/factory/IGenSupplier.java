package io.dummymaker.factory;

import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * Config that providers contract for generators discovery Based on fields and
 * classes for auto generation
 *
 * @author GoodforGod
 * @see io.dummymaker.annotation.special.GenAuto
 * @since 27.07.2019
 */
public interface IGenSupplier {

    /**
     * Finds suitable generator for field from all available generators
     *
     * @param field target
     * @return suitable generator
     */
    @NotNull
    Class<? extends IGenerator> getSuitable(@NotNull Field field);

    /**
     * Finds suitable generator for field from all available generators
     *
     * @param field target
     * @param type  target that can sometimes differ from field type Like parent
     *              class, or implementation not interface
     * @return suitable generator
     */
    @NotNull
    Class<? extends IGenerator> getSuitable(@NotNull Field field, @Nullable Class<?> type);
}
