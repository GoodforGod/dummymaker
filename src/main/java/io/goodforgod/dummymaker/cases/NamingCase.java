package io.goodforgod.dummymaker.cases;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/**
 * Allow applying naming strategy for string values
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public interface NamingCase extends Function<CharSequence, CharSequence> {

    /**
     * Format value using naming strategy
     *
     * @param value value to formatted via strategy
     * @return formatted value
     */
    @NotNull
    @Override
    CharSequence apply(@NotNull CharSequence value);
}
