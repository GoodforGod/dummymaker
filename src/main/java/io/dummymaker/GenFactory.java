package io.dummymaker;

import io.dummymaker.cases.NamingCase;
import io.dummymaker.generator.Localisation;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

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
    <T> T build(@NotNull Class<T> target);

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
    <T> @NotNull List<T> build(@NotNull Class<T> target, int amount);

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
    <T> @NotNull Stream<T> stream(@NotNull Class<T> target, long amount);

    /**
     * {@link #stream(Class, long)}
     */
    <T> @NotNull Stream<T> stream(@NotNull Supplier<T> supplier, long amount);

    interface Builder {

        @NotNull
        Builder addRule(@NotNull GenRule rule);

        @NotNull
        Builder seed(long seed);

        /**
         * @param namingCase to apply for {@link CharSequence} values
         * @return self
         */
        @NotNull
        Builder applyCase(@NotNull NamingCase namingCase);

        /**
         * @param localisation to use when generating {@link CharSequence} values
         * @return self
         */
        @NotNull
        Builder localisation(@NotNull Localisation localisation);

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
