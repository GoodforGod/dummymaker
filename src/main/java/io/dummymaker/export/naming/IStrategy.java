package io.dummymaker.export.naming;

/**
 * Contract for naming strategy used in exporters
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public interface IStrategy {

    /**
     * Convert value using naming strategy
     *
     * @param value value to convert via strategy
     * @return converted value
     */
    String toStrategy(final String value);
}
