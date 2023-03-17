package io.dummymaker;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.GenDepth;
import io.dummymaker.cases.NamingCase;
import io.dummymaker.generator.Localisation;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

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
    <T> @NotNull List<T> build(@NotNull Class<T> target, @Range(from = 0, to = Integer.MAX_VALUE) int amount);

    /**
     * {@link #build(Class, int)}
     */
    <T> @NotNull List<T> build(@NotNull Supplier<T> supplier, @Range(from = 0, to = Integer.MAX_VALUE) int amount);

    /**
     * Instantiates class instance and populate its fields
     *
     * @param target class to build and fill with data
     * @param amount of objects to produce
     * @param <T>    object type
     * @return generates class filled with data
     */
    <T> @NotNull Stream<T> stream(@NotNull Class<T> target, @Range(from = 0, to = Long.MAX_VALUE) long amount);

    /**
     * {@link #stream(Class, long)}
     */
    <T> @NotNull Stream<T> stream(@NotNull Supplier<T> supplier, @Range(from = 0, to = Long.MAX_VALUE) long amount);

    interface Builder {

        @NotNull
        Builder addRule(@NotNull GenRule rule);

        /**
         * @param seed to use when selecting default generators for random field value generation
         * @return self
         */
        @NotNull
        Builder seed(long seed);

        /**
         * @param namingCase to apply for {@link CharSequence} values
         * @return self
         */
        @NotNull
        Builder applyCase(@NotNull NamingCase namingCase);

        /**
         * Is selected
         * 
         * @param localisation to use when generating {@link CharSequence} values
         * @return self
         */
        @NotNull
        Builder localisation(@NotNull Localisation localisation);

        /**
         * True by default
         * 
         * @see GenAuto
         * @param autoByDefault if TRUE than generate random field values for classes
         * @return self
         */
        @NotNull
        Builder autoByDefault(boolean autoByDefault);

        /**
         * {@link GenDepth#DEFAULT} by default
         * 
         * @see GenDepth
         * @param depthMax to use when generating embedded classes
         * @return self
         */
        @NotNull
        Builder depthByDefault(@Range(from = 0, to = GenDepth.MAX) int depthMax);

        /**
         * False by default
         * 
         * @param ignoreExceptions if TRUE than will ignore exceptions that may occur
         * @return self
         */
        @NotNull
        Builder ignoreExceptions(boolean ignoreExceptions);

        /**
         * True by default
         * 
         * @param overrideDefaultValues if TRUE than will generate random values for field with NOT NULL
         *                              values
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
