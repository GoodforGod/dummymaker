package io.dummymaker.generator;

import io.dummymaker.annotation.core.PrimeGen;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generator used by PrimeGen to populate field
 *
 * @author GoodforGod
 * @see PrimeGen
 * @since 26.05.2017
 */
public interface IGenerator<T> extends Comparable<IGenerator> {

    @Nullable
    T generate();

    @Nullable
    default Pattern pattern() {
        return null;
    }

    default int order() {
        return 0;
    }

    @Override
    default int compareTo(@NotNull IGenerator o) {
        return Integer.compare(order(), o.order());
    }
}
