package io.dummymaker.export;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/**
 * Allow applying naming strategy for field names or class name during export
 *
 * @author Anton Kurako (GoodforGod)
 * @see Exporter
 * @since 21.02.2018
 */
public interface Case extends Function<String, String> {

    /**
     * Format value using naming strategy
     *
     * @param value value to formatted via strategy
     * @return formatted value
     */
    @NotNull
    @Override
    String apply(@NotNull String value);
}
