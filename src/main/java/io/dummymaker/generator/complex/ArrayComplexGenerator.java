package io.dummymaker.generator.complex;

import io.dummymaker.annotation.complex.GenArray;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.simple.string.IdGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Generates arrays based on field type
 *
 * @author GoodforGod
 * @see io.dummymaker.annotation.complex.GenArray
 * @see IComplexGenerator
 * @see CollectionComplexGenerator
 * @since 04.11.2018
 */
public class ArrayComplexGenerator extends CollectionComplexGenerator {

    @Override
    public Object generate(final @NotNull Class<?> parent,
                           final @NotNull Field field,
                           final @NotNull IGenStorage storage,
                           final Annotation annotation,
                           final int depth) {
        if (!field.getType().getTypeName().endsWith("[]"))
            return null;

        final Class<?> valueClass = field.getType().getComponentType();
        if (annotation == null) {
            final int size = CollectionUtils.random(MIN_DEFAULT, MAX_DEFAULT);
            final int maxDepth = storage.getDepth(parent, valueClass);
            return genArray(size, valueClass, suitable(storage, field, valueClass), storage, depth, maxDepth);
        }

        final GenArray a = ((GenArray) annotation);
        final Class<? extends IGenerator> generatorClass = isGenDefault(a.value())
                ? suitable(storage, field, valueClass)
                : a.value();

        final int size = getDesiredSize(a.min(), a.max(), a.fixed());
        return genArray(size, valueClass, generatorClass, storage, depth, a.depth());
    }

    @Override
    public Object generate() {
        final int size = CollectionUtils.random(MIN_DEFAULT, MAX_DEFAULT);
        return genArray(size, String.class, IdGenerator.class, null, GenEmbedded.MAX, 1);
    }

    Object genArray(final int size,
                    final Class<?> valueClass,
                    final Class<? extends IGenerator> valueGenerator,
                    final IGenStorage storage,
                    final int depth,
                    final int maxDepth) {

        // Firstly try to generate initial object, so we won't allocate list if not
        // necessary
        final Object initial = generateValue(valueGenerator, valueClass, storage, depth, maxDepth);
        if (initial == null)
            return null;

        final Object array = Array.newInstance(valueClass, size);
        Array.set(array, 0, initial);
        for (int i = 1; i < size; i++) {
            final Object o = generateValue(valueGenerator, valueClass, storage, depth, maxDepth);
            Array.set(array, i, o);
        }

        return array;
    }
}
