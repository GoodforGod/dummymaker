package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import io.dummymaker.generator.simple.number.UnixTimeGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.dummymaker.util.CastUtils.castToNumber;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.12.2022
 */
public final class UnixTimeParameterizedGenerator implements ParameterizedGenerator<Object> {

    private static final UnixTimeGenerator UNIX_TIME_GENERATOR = new UnixTimeGenerator();

    private final long fromUnixTime;
    private final long toUnixTime;

    public UnixTimeParameterizedGenerator(long fromUnixTime, long toUnixTime) {
        this.fromUnixTime = fromUnixTime;
        this.toUnixTime = toUnixTime;
    }

    @Override
    public @Nullable Object get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        final Class<?> fieldClass = fieldType.value();
        final long unixTime = UNIX_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
        if (fieldClass.isAssignableFrom(Number.class)) {
            return castToNumber(unixTime, fieldClass);
        }

        return unixTime;
    }

    @Override
    public Object get() {
        return UNIX_TIME_GENERATOR.get(fromUnixTime, toUnixTime);
    }
}
