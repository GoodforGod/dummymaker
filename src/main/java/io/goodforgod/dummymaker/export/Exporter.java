package io.goodforgod.dummymaker.export;

import java.util.Collection;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Allow to export objects in implementation specific format
 *
 * @author Anton Kurako (GoodforGod)
 * @see Format
 * @since 26.05.2017
 */
public interface Exporter {

    /**
     * Exporting single object to file
     *
     * @param value object to export
     */
    void exportAsFile(@Nullable Object value);

    /**
     * Exporting collection of objects to file
     *
     * @param collection objects to export
     */
    void exportAsFile(@NotNull Collection<?> collection);

    /**
     * Exporting steam of objects to file
     *
     * @param stream of objects to export
     * @param type   value type class
     * @param <T>    value type
     */
    <T> void streamToFile(@NotNull Stream<T> stream, Class<T> type);

    /**
     * Exporting single object as string value
     *
     * @param value object to export
     * @return object string representation
     */
    @NotNull
    String exportAsString(@Nullable Object value);

    /**
     * Exporting collection of objects as string value
     *
     * @param collection objects to export
     * @return objects string representation
     */
    @NotNull
    String exportAsString(@NotNull Collection<?> collection);

    /**
     * Exporting steam of objects as string value
     *
     * @param stream of objects to export
     * @param type   value type class
     * @param <T>    value type
     * @return objects string representation
     */
    @NotNull
    <T> String streamToString(@NotNull Stream<T> stream, Class<T> type);
}
