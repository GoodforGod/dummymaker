package io.dummymaker.export;

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
    String format(final String value);
}
