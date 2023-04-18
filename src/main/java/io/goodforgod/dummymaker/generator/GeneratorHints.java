package io.goodforgod.dummymaker.generator;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @see Generator.Hints
 * @author Anton Kurako (GoodforGod)
 * @since 09.04.2023
 */
final class GeneratorHints implements Generator.Hints {

    static final int DEFAULT_PRIORITY = 0;
    static final Generator.Hints EMPTY = new GeneratorHints(null, DEFAULT_PRIORITY);

    @Nullable
    private final Pattern pattern;
    private final int priority;

    GeneratorHints(@Nullable Pattern pattern, int priority) {
        this.pattern = pattern;
        this.priority = priority;
    }

    @Override
    public @Nullable Pattern pattern() {
        return pattern;
    }

    public int priority() {
        return priority;
    }

    static final class GeneratorHintsBuilder implements Generator.Hints.Builder {

        @Nullable
        private Pattern pattern;
        private int priority = DEFAULT_PRIORITY;

        @Override
        public @NotNull Builder withPattern(@NotNull Pattern pattern) {
            this.pattern = pattern;
            return this;
        }

        public @NotNull Builder withPriority(int priority) {
            this.priority = priority;
            return this;
        }

        @NotNull
        @Override
        public Generator.Hints build() {
            return new GeneratorHints(pattern, priority);
        }
    }
}
