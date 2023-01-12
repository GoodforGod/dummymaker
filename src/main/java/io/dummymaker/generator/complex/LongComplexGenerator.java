package io.dummymaker.generator.complex;

import io.dummymaker.annotation.simple.number.GenInt;
import io.dummymaker.annotation.simple.number.GenLong;
import io.dummymaker.factory.old.GenStorage;
import io.dummymaker.util.RandomUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generates integer/long values in specified range
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenInt
 * @see GenLong
 * @see ComplexGenerator
 * @since 21.07.2019
 */
public class LongComplexGenerator implements ComplexGenerator {

    @Override
    public @Nullable Object generate(final @NotNull Class<?> parent,
                                     final @NotNull Field field,
                                     final @NotNull GenStorage storage,
                                     final Annotation annotation,
                                     final int depth) {
        final long from = getFrom(annotation);
        final long to = getTo(annotation);
        return RandomUtils.random(from, to);
    }

    private long getFrom(Annotation annotation) {
        if (!GenInt.class.equals(annotation.annotationType()) && !GenLong.class.equals(annotation.annotationType())) {
            return Long.MIN_VALUE;
        }

        return (GenLong.class.equals(annotation.annotationType()))
                ? ((GenLong) annotation).from()
                : ((GenInt) annotation).from();
    }

    private long getTo(Annotation annotation) {
        if (!GenInt.class.equals(annotation.annotationType()) && !GenLong.class.equals(annotation.annotationType())) {
            return Long.MAX_VALUE;
        }

        return (GenLong.class.equals(annotation.annotationType()))
                ? ((GenLong) annotation).to()
                : ((GenInt) annotation).to();
    }

    @Override
    public @NotNull Object get() {
        return RandomUtils.random(Long.MAX_VALUE);
    }
}
