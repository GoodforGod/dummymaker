package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadLocalRandom;

import static io.dummymaker.util.CastUtils.getGenericType;

/**
 * Generates Set or GenSet annotation
 *
 * @author GoodforGod
 * @see GenSet
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @see CollectionComplexGenerator
 * @since 21.04.2018
 */
public class SetComplexGenerator extends CollectionComplexGenerator {

    @SuppressWarnings("Duplicates")
    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final IGenStorage storage,
                           final int depth) {
        if (!field.getType().isAssignableFrom(Set.class))
            return null;

        final Class<?> valueClass = (Class<?>) getGenericType(field.getGenericType());
        if (annotation == null) {
            final int size = ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT);
            final Class<? extends IGenerator> suitable = suitable(storage, field, valueClass);
            return genCollection(size, buildCollection(field, size), suitable, valueClass, storage, depth, 1);
        }

        final GenSet a = ((GenSet) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? suitable(storage, field, valueClass)
                : a.value();

        final int size = getDesiredSize(a.min(), a.max(), a.fixed());
        return genCollection(size, buildCollection(field, size), generatorClass, valueClass, storage, depth, a.depth());
    }

    @Override
    public Object generate() {
        final int size = ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT);
        final Set collection = buildCollection(null, size);
        return genCollection(size, collection, IdGenerator.class, Object.class, null, GenEmbedded.MAX, 1);
    }

    private Set buildCollection(Field field, int size) {
        if (field == null)
            return new HashSet<>(size);

        if (TreeSet.class.equals(field.getType())) {
            return new TreeSet<>();
        } else if (ConcurrentSkipListSet.class.equals(field.getType())) {
            return new ConcurrentSkipListSet<>();
        } else if (LinkedHashSet.class.equals(field.getType())) {
            return new LinkedHashSet<>(size);
        } else if (CopyOnWriteArraySet.class.equals(field.getType())) {
            return new CopyOnWriteArraySet<>();
        }

        return new HashSet<>(size);
    }
}
