package io.goodforgod.dummymaker;

import java.lang.reflect.Type;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Represent Generated Type
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public interface GenType {

    /**
     * @return plain type and its generics as flatten plain list
     */
    @NotNull
    List<GenType> flatten();

    /**
     * @return Plain type as class
     */
    @NotNull
    Class<?> raw();

    /**
     * @return type generics
     */
    @NotNull
    List<GenType> generics();

    @NotNull
    static GenType ofType(@NotNull Type type) {
        return DefaultGenType.ofType(type);
    }
}
