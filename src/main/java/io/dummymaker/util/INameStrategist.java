package io.dummymaker.util;

import static io.dummymaker.util.NameStrategist.NamingStrategy;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 02.09.2017
 */
public interface INameStrategist {
    String toNamingStrategy(final String value, final NamingStrategy strategy);
}
