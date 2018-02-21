package io.dummymaker.util;

import static io.dummymaker.util.NameStrategist.NamingStrategy;

/**
 * Contact for strategist, which convert string value with chosen name strategy
 *
 * @see NameStrategist.NamingStrategy
 *
 * @author GoodforGod
 * @since 02.09.2017
 */
public interface INameStrategist {
    String toNamingStrategy(final String value, final NamingStrategy strategy);
}
