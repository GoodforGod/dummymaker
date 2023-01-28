package io.dummymaker.generator;

import io.dummymaker.annotation.GenCustom;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

/**
 * Generator used by GenCustom to populate field
 * Must have Zero Argument constructor
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenCustom
 * @since 26.05.2017
 */
public interface Generator<T> extends Supplier<T>, Ordered {

    /**
     * @return pattern used to associated generator by Field Name when choosing generator
     */
    @Nullable
    default Pattern pattern() {
        return null;
    }
}
