package io.dummymaker.generator;

import org.jetbrains.annotations.NotNull;

/**
 * Generator that uses localization to generated local specific value
 *
 * @author Anton Kurako (GoodforGod)
 * @since 23.01.2023
 */
public interface LocalizedGenerator<T> extends Generator<T> {

    /**
     * @param localisation to use when generation value
     * @return newly generated value
     */
    T get(@NotNull Localisation localisation);

    @Override
    default T get() {
        return get(Localisation.DEFAULT);
    }
}
