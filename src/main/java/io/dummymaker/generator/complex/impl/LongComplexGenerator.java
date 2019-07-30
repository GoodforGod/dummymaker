package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.simple.number.GenInteger;
import io.dummymaker.annotation.simple.number.GenLong;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.simple.impl.number.IntegerGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

import static io.dummymaker.util.CastUtils.castObject;

/**
 * Generates integer/long values in specified range
 *
 * @author GoodforGod
 * @see GenInteger
 * @see GenLong
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @since 21.07.2019
 */
public class LongComplexGenerator extends BasicComplexGenerator {

    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final IGenStorage storage,
                           final int depth) {
        if (field == null || castObject(1, field.getType()) == null)
            return null;

        final long from = getFrom(annotation);
        final long to = getTo(annotation);

        return ThreadLocalRandom.current().nextLong(from, to);
    }

    private long getFrom(Annotation annotation) {
        if (!GenInteger.class.equals(annotation.annotationType()) && !GenLong.class.equals(annotation.annotationType()))
            return 0;

        return (GenLong.class.equals(annotation.annotationType()))
                ? ((GenLong) annotation).from()
                : ((GenInteger) annotation).from();
    }

    private long getTo(Annotation annotation) {
        if (!GenInteger.class.equals(annotation.annotationType()) && !GenLong.class.equals(annotation.annotationType()))
            return Integer.MAX_VALUE;

        return (GenLong.class.equals(annotation.annotationType()))
                ? ((GenLong) annotation).to()
                : ((GenInteger) annotation).to();
    }

    @Override
    public Object generate() {
        return new IntegerGenerator().generate();
    }
}
