package io.dummymaker.export;


import org.jetbrains.annotations.NotNull;


/**
 * Allow to apply naming strategy for field names or class name during export
 *
 * @author GoodforGod
 * @see io.dummymaker.export.IExporter
 * @since 21.02.2018
 */
public interface ICase {

    /**
     * Format value using naming strategy
     *
     * @param value value to formatted via strategy
     * @return formatted value
     */
    @NotNull
    String format(@NotNull String value);
}
