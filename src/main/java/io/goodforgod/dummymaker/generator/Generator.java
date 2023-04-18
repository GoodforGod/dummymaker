package io.goodforgod.dummymaker.generator;

import io.goodforgod.dummymaker.annotation.GenCustom;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generator used by GenCustom to populate field
 * Must have Zero Argument constructor
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenCustom
 * @since 26.05.2017
 */
@FunctionalInterface
public interface Generator<T> extends Supplier<T> {

    /**
     * @return pattern used to associated generator by Field Name when choosing generator
     */
    @NotNull
    default Hints hints() {
        return GeneratorHints.EMPTY;
    }

    interface Hints {

        /**
         * @return pattern used to associated generator by Field Name when choosing generator
         */
        @Nullable
        Pattern pattern();

        /**
         * @return the more value the more priority generator have over others
         */
        int priority();

        @NotNull
        static Builder builder() {
            return new GeneratorHints.GeneratorHintsBuilder();
        }

        interface Builder {

            /**
             * @return pattern used to associated generator by Field Name when choosing generator
             */
            @NotNull
            Builder withPattern(@NotNull Pattern pattern);

            /**
             * @return the more value the more priority generator have over others
             */
            @NotNull
            Builder withPriority(int priority);

            @NotNull
            Hints build();
        }
    }
}
