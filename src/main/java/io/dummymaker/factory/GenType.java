package io.dummymaker.factory;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Represent generated type
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public interface GenType {

    /**
     * @return RAW class type as plain without any Array extension
     */
    @NotNull
    Class<?> plain();

    /**
     * @return plain type and its generics as flatten plain list
     */
    @NotNull
    List<GenType> flatten();

    /**
     * @return RAW class type as it was extracted
     */
    @NotNull
    Class<?> raw();

    @NotNull
    List<GenType> generics();
}
