package io.dummymaker.bundle;

import io.dummymaker.generator.Localisation;
import org.jetbrains.annotations.NotNull;

/**
 * Provides default bundles of values for generators
 *
 * @author Anton Kurako (GoodforGod)
 * @since 31.05.2017
 */
public interface Bundle {

    /**
     * @return random bundle value
     */
    @NotNull
    String random(@NotNull Localisation localisation);
}
