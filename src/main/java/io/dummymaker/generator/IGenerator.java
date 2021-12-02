package io.dummymaker.generator;

import io.dummymaker.annotation.core.PrimeGen;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

/**
 * Generator used by PrimeGen to populate field
 *
 * @author GoodforGod
 * @see PrimeGen
 * @since 26.05.2017
 */
public interface IGenerator<T> {

    @Nullable
    T generate();

    @Nullable
    default Pattern pattern() {
        return null;
    }
}
