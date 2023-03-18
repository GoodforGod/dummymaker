package io.dummymaker.export;

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
     * Allow exporting single object
     *
     * @param value object to export
     */
    void exportAsFile(@Nullable Object value);

    /**
     * Allow exporting collection of objects
     *
     * @param collection objects to export
     */
    void exportAsFile(@NotNull Collection<?> collection);

    /**
     * Allow exporting steam of objects
     *
     * @param stream of objects to export
     */
    <T> void streamToFile(@NotNull Stream<T> stream, Class<T> type);

    /**
     * Allow exporting single object as a single string value
     *
     * @param value object to export
     * @return object string representation
     */
    @NotNull
    String exportAsString(@Nullable Object value);

    /**
     * Allow exporting collection of objects as single a string value
     *
     * @param collection objects to export
     * @return objects string representation
     */
    @NotNull
    String exportAsString(@NotNull Collection<?> collection);

    /**
     * Allow exporting steam of objects as single a string value
     *
     * @param stream of objects to export
     * @return objects string representation
     */
    @NotNull
    <T> String streamToString(@NotNull Stream<T> stream, Class<T> type);
}
