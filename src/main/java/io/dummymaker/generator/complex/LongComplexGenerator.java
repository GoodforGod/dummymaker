package io.dummymaker.generator.complex;

import io.dummymaker.annotation.simple.number.GenInt;
import io.dummymaker.annotation.simple.number.GenLong;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.simple.number.IntegerGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

import static io.dummymaker.util.CastUtils.castObject;

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
    public Object generate(final Annotation annotation,
                           final Field field,
                           final IGenStorage storage,
                           final int depth) {
        if (castObject(1, field.getType()) == null)
            return null;

        final long from = getFrom(annotation);
        final long to = getTo(annotation);

        return ThreadLocalRandom.current().nextLong(from, to);
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
    public Object generate() {
        return new IntegerGenerator().generate();
    }
}
