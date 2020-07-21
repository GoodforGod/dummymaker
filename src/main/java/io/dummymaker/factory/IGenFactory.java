package io.dummymaker.factory;

import io.dummymaker.export.IExporter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Factory that generates data objects Core that handles all top level logic
 *
 * @author GoodforGod
 * @see io.dummymaker.factory.impl.GenFactory
 * @since 21.07.2019
 */
public interface IGenFactory {

    /**
     * Instantiates class instance and populate its fields
     *
     * @param target class to build and fill with data
     * @param <T>    object type
     * @return generates class filled with data
     */
    <T> @Nullable T build(@Nullable Class<T> target);

    /**
     * Instantiates class instance and populate its fields
     *
     * @param supplier of class to build
     * @param <T>      object type
     * @return generates class filled with data
     */
    <T> @Nullable T build(@NotNull Supplier<T> supplier);

    /**
     * Instantiates class instance and populate its fields
     *
     * @param target class to build and fill with data
     * @param amount of objects to produce
     * @param <T>    object type
     * @return generates class filled with data
     */
    <T> @NotNull List<T> build(@Nullable Class<T> target, int amount);

    /**
     * Instantiates class instance and populate its fields Use in case class have no
     * empty constructor of have complex initialization logic
     *
     * @param supplier of class to build
     * @param amount   of objects to produce
     * @param <T>      object type
     * @return generates class filled with data
     */
    <T> @NotNull List<T> build(@NotNull Supplier<T> supplier, int amount);

    /**
     * Instantiates class instance and populate its fields
     *
     * @param target class to build and fill with data
     * @param amount of objects to produce
     * @param <T>    object type
     * @return generates class filled with data
     */
    <T> @NotNull Stream<T> stream(@Nullable Class<T> target, int amount);

    /**
     * Instantiates class instance and populate its fields Use in case class have no
     * empty constructor of have complex initialization logic
     *
     * @param supplier of class to build
     * @param amount   of objects to produce
     * @param <T>      object type
     * @return generates class filled with data
     */
    <T> @NotNull Stream<T> stream(@NotNull Supplier<T> supplier, int amount);

    /**
     * Instantiates classes and them streamline to file via exporter
     *
     * @param target   class to build and fill with data
     * @param amount   of objects to produce
     * @param exporter to feed data for export
     * @param <T>      object type
     * @return generates class filled with data
     */
    <T> boolean export(@Nullable Class<T> target, int amount, @NotNull IExporter exporter);

    /**
     * Populates dummy object fields
     *
     * @param t   object to fill with data
     * @param <T> object type
     * @return Populated Object
     */
    <T> @Nullable T fill(@Nullable T t);

    /**
     * Populates dummy object fields
     *
     * @param stream of objects to fill with data
     * @param <T>    object type
     * @return Populated Object
     */
    <T> @NotNull Stream<T> fill(@Nullable Stream<T> stream);

    /**
     * Populates dummy object fields
     *
     * @param collection of objects to fill with data
     * @param <T>        object type
     * @return Populated Object
     */
    <T> @NotNull List<T> fill(@Nullable Collection<T> collection);
}
