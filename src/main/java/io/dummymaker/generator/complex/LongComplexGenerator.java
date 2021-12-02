package io.dummymaker.generator.complex;

import static io.dummymaker.util.CastUtils.castObject;

import io.dummymaker.annotation.simple.number.GenInt;
import io.dummymaker.annotation.simple.number.GenLong;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.util.CollectionUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generates integer/long values in specified range
 *
 * @author GoodforGod
 * @see GenInt
 * @see GenLong
 * @see IComplexGenerator
 * @since 21.07.2019
 */
public class LongComplexGenerator implements IComplexGenerator {

    @Override
    public @Nullable Object generate(final @NotNull Class<?> parent,
                                     final @NotNull Field field,
                                     final @NotNull IGenStorage storage,
                                     final Annotation annotation,
                                     final int depth) {
        if (castObject(1, field.getType()) == null)
            return null;

        final long from = getFrom(annotation);
        final long to = getTo(annotation);

        return CollectionUtils.random(from, to);
    }

    private long getFrom(Annotation annotation) {
        if (!GenInt.class.equals(annotation.annotationType()) && !GenLong.class.equals(annotation.annotationType()))
            return Integer.MIN_VALUE;

        return (GenLong.class.equals(annotation.annotationType()))
                ? ((GenLong) annotation).from()
                : ((GenInt) annotation).from();
    }

    private long getTo(Annotation annotation) {
        if (!GenInt.class.equals(annotation.annotationType()) && !GenLong.class.equals(annotation.annotationType()))
            return Integer.MAX_VALUE;

        return (GenLong.class.equals(annotation.annotationType()))
                ? ((GenLong) annotation).to()
                : ((GenInt) annotation).to();
    }

    @Override
    public @NotNull Object generate() {
        return CollectionUtils.random(Long.MAX_VALUE);
    }
}
