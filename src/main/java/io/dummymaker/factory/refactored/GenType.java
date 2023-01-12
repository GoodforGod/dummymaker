package io.dummymaker.factory.refactored;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public interface GenType {

    @NotNull
    Class<?> plain();

    @NotNull
    List<GenType> flatten();

    @NotNull
    Class<?> raw();

    @NotNull
    List<GenType> generics();
}
