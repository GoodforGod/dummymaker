package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenEnum;
import io.dummymaker.container.impl.GeneratorsStorage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 01.03.2019
 */
public class EnumComplexGenerator extends BasicComplexGenerator {

    @SuppressWarnings("unchecked")
    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final GeneratorsStorage storage,
                           final int depth) {
        if (field == null)
            return null;

        final int from = (annotation == null) ? 0 : ((GenEnum) annotation).from();
        final int to = (annotation == null) ? GenEnum.MAX : ((GenEnum) annotation).to();

        final int length = field.getType().getFields().length;
        final int usedLength = (length < to) ? length : to;
        final int i = ThreadLocalRandom.current().nextInt(from, usedLength);

        return Enum.valueOf((Class<? extends Enum>)field.getType(), field.getType().getFields()[i].getName());
    }

    @Override
    public Object generate() {
        return 0;
    }
}
