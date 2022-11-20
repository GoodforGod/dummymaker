package io.dummymaker.generator;

import io.dummymaker.annotation.GenCustom;

import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Nullable;

/**
 * Generator used by PrimeGen to populate field
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenCustom
 * @since 26.05.2017
 */
public interface Generator<T> extends Supplier<T>, Ordered {

    @Nullable
    default Pattern pattern() {
        return null;
    }
}
