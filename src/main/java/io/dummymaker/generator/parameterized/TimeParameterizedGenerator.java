package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 02.12.2022
 */
public final class TimeParameterizedGenerator implements ParameterizedGenerator<Object> {

    private final long fromUnixTime;
    private final long toUnixTime;
    private final String formatter;

    public TimeParameterizedGenerator(long fromUnixTime, long toUnixTime, String formatter) {
        this.fromUnixTime = fromUnixTime;
        this.toUnixTime = toUnixTime;
        this.formatter = formatter;
    }

    @Override
    public @Nullable Object get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        return null;
    }

    @Override
    public Object get() {
        return null;
    }
}
