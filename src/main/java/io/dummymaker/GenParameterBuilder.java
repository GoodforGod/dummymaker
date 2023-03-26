package io.dummymaker;

import io.dummymaker.generator.parameterized.ListParameterizedGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is used to build generic field random values:
 * For example field with type
 * <p>
 * {@code For example field with type: <code>private List<String> emails</code>}
 * <p>
 * This {@link GenParameterBuilder} will be used to build {@link String} values inside
 * {@link ListParameterizedGenerator}
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public interface GenParameterBuilder {

    /**
     * @param type to generate
     * @param <T>  erasure
     * @return type instance with generated fields
     */
    @Nullable
    <T> T build(@NotNull Class<T> type);
}
