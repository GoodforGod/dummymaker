package io.goodforgod.dummymaker.bundle;

import io.goodforgod.dummymaker.generator.Localisation;
import org.jetbrains.annotations.NotNull;

/**
 * Provides default bundles of values for generators
 *
 * @author Anton Kurako (GoodforGod)
 * @since 31.05.2017
 */
public interface Bundle {

    /**
     * @param localisation to use for random value generation
     * @return random bundle value
     */
    @NotNull
    String random(@NotNull Localisation localisation);
}
