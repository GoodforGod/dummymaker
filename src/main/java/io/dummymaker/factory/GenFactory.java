package io.dummymaker.factory;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Factory that generates data objects Core that handles all top level logic
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.07.2019
 */
public interface GenFactory {

    /**
     * Instantiates class instance and populate its fields
     *
     * @param target class to build and fill with data
     * @param <T>    object type
     * @return generates class filled with data
     */
    <T> T build(@Nullable Class<T> target);

    /**
     * {@link #build(Class)}
     */
    <T> T build(@NotNull Supplier<T> supplier);

    /**
     * Instantiates class instance and populate its fields
     *
     * @param target class to build and fill with data
     * @param amount of objects to produce
     * @param <T>    object type
     * @return generates class filled with data
     */
    <T> @NotNull List<T> build(@Nullable Class<T> target, int amount);

    /**
     * {@link #build(Class, int)}
     */
    <T> @NotNull List<T> build(@NotNull Supplier<T> supplier, int amount);

    /**
     * Instantiates class instance and populate its fields
     *
     * @param target class to build and fill with data
     * @param amount of objects to produce
     * @param <T>    object type
     * @return generates class filled with data
     */
    <T> @NotNull Stream<T> stream(@Nullable Class<T> target, long amount);

    /**
     * {@link #stream(Class, long)}
     */
    <T> @NotNull Stream<T> stream(@NotNull Supplier<T> supplier, long amount);

    interface Builder {

        @NotNull
        Builder seed(long seed);

        @NotNull
        Builder rule(@NotNull GenRule rule);

        /**
         * @param autoByDefault is true by Default
         * @return self
         */
        @NotNull
        Builder autoByDefault(boolean autoByDefault);

        @NotNull
        Builder depthByDefault(int depthMax);

        /**
         * @param ignoreExceptions is false by Default
         * @return self
         */
        @NotNull
        Builder ignoreExceptions(boolean ignoreExceptions);

        /**
         * @param overrideDefaultValues is true by Default
         * @return self
         */
        @NotNull
        Builder overrideDefaultValues(boolean overrideDefaultValues);

        @NotNull
        GenFactory build();
    }

    @NotNull
    static Builder builder() {
        return new DefaultGenFactoryBuilder();
    }

    @NotNull
    static GenFactory build() {
        return builder().build();
    }
}
