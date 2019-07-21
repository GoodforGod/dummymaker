package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.factory.IComplexService;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicGenUtils.getAutoGenerator;

/**
 * Generates List for GenList annotation
 *
 * @author GoodforGod
 * @see GenList
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @see CollectionComplexGenerator
 * @since 21.04.2018
 */
public class ListComplexGenerator extends CollectionComplexGenerator {

    @SuppressWarnings("Duplicates")
    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final IComplexService storage,
                           final int depth) {
        if (field == null || !field.getType().isAssignableFrom(List.class))
            return null;

        final Class<?> valueClass = (Class<?>) getGenericType(field.getGenericType());
        if (annotation == null) {
            final int size = ThreadLocalRandom.current().nextInt(MIN_COUNT_DEFAULT, MAX_COUNT_DEFAULT);
            return genCollection(size,
                    buildCollection(field, size),
                    getAutoGenerator(valueClass),
                    valueClass,
                    storage,
                    depth,
                    1);
        }

        final GenList a = ((GenList) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? getAutoGenerator(valueClass)
                : a.value();

        final int size = getDesiredSize(a.min(), a.max(), a.fixed());
        return genCollection(size,
                buildCollection(field, size),
                generatorClass,
                valueClass,
                storage,
                depth,
                a.depth());
    }

    @Override
    public Object generate() {
        final int size = ThreadLocalRandom.current().nextInt(MIN_COUNT_DEFAULT, MAX_COUNT_DEFAULT);
        return genCollection(size,
                buildCollection(null, size),
                IdGenerator.class,
                String.class,
                null,
                GenEmbedded.MAX,
                1);
    }

    private <T> List<T> buildCollection(Field field, int size) {
        if (field == null)
            return new ArrayList<>(size);

        if (LinkedList.class.equals(field.getType())) {
            return new LinkedList<>();
        } else if (CopyOnWriteArrayList.class.equals(field.getType())) {
            return new CopyOnWriteArrayList<>();
        }

        return new ArrayList<>(size);
    }
}
