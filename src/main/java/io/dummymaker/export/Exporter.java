package io.dummymaker.export;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Allow to export objects in desired format
 *
 * @author Anton Kurako (GoodforGod)
 * @see Format
 * @since 26.05.2017
 */
public interface Exporter {

    /**
     * Allow exporting single object
     *
     * @param value object to export
     * @param <T>   object type
     * @return true if export successful
     */
    <T> boolean exportAsFile(@Nullable T value);

    /**
     * Allow exporting collection of objects
     *
     * @param collection objects to export
     * @param <T>        object type
     * @return true if export successful
     */
    <T> boolean exportAsFile(@Nullable Collection<T> collection);

    /**
     * Allow exporting single object as a single string value
     *
     * @param value object to export
     * @param <T>   object type
     * @return object string representation
     */
    @NotNull
    <T> String exportAsString(@Nullable T value);

    /**
     * Allow exporting list of objects as single a string value
     *
     * @param <T>        object type
     * @param collection objects to export
     * @return objects string representation
     */
    @NotNull
    <T> String exportAsString(@NotNull Collection<T> collection);
}
