package io.dummymaker.generator.complex;

import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.simple.string.IdGenerator;
import io.dummymaker.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

import static io.dummymaker.util.CastUtils.getGenericType;

/**
 * Generates Set or GenSet annotation
 *
 * @author GoodforGod
 * @see GenSet
 * @see IComplexGenerator
 * @see CollectionComplexGenerator
 * @since 21.04.2018
 */
public class SetComplexGenerator extends CollectionComplexGenerator {

    @SuppressWarnings("Duplicates")
    @Override
    public Object generate(final Class<?> parent,
                           final Field field,
                           final IGenStorage storage,
                           final Annotation annotation,
                           final int depth) {
        if (!Set.class.isAssignableFrom(field.getType()))
            return null;

        final Class<?> valueClass = (Class<?>) getGenericType(field.getGenericType());
        if (annotation == null) {
            final int size = CollectionUtils.random(MIN_DEFAULT, MAX_DEFAULT);
            final Class<? extends IGenerator> suitable = suitable(storage, field, valueClass);
            final int maxDepth = storage.getDepth(parent, field.getType());
            return genCollection(size, buildCollection(field, size), suitable, valueClass, storage, depth, maxDepth);
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
        final int size = CollectionUtils.random(MIN_DEFAULT, MAX_DEFAULT);
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
