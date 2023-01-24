package io.dummymaker.bundle;

import io.dummymaker.generator.Localisation;
import io.dummymaker.util.CollectionUtils;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Prime bundle implementation
 *
 * @author Anton Kurako (GoodforGod)
 * @see Bundle
 * @since 31.05.2017
 */
abstract class AbstractBundle implements Bundle {

    private final List<String> english = getEnglish();
    private final List<String> russian = getRussian();

    abstract List<String> getEnglish();

    abstract List<String> getRussian();

    @Override
    public @NotNull String random(@NotNull Localisation localisation) {
        switch (localisation) {
            case RUSSIAN:
                return CollectionUtils.random(russian);
            case ENGLISH:
                return CollectionUtils.random(english);
            default:
                throw new IllegalStateException("Unknown localisation: " + localisation);
        }
    }
}
