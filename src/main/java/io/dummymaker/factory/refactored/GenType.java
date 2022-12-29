package io.dummymaker.factory.refactored;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public interface GenType {

    @NotNull
    Class<?> value();

    @NotNull
    List<GenType> generics();
}
