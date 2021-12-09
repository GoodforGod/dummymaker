package io.dummymaker.generator.complex;


import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.IGenerator;
import java.util.*;
import org.jetbrains.annotations.NotNull;


/**
 * Basic collection complex generator implementation Can be used by other collection complex
 * generators
 *
 * @author GoodforGod
 * @see IComplexGenerator
 * @since 22.04.2018
 */
abstract class CollectionComplexGenerator extends BasicComplexGenerator {

    @SuppressWarnings("unchecked")
    @NotNull
    Collection genCollection(final int size,
                             final Collection<?> collection,
                             final Class<? extends IGenerator> valueGenerator,
                             final Class<?> fieldClass,
                             final IGenStorage storage,
                             final int depth,
                             final int maxDepth) {

        // Firstly try to generate initial object, so we won't allocate list if not
        // necessary
        final Object initial = generateValue(valueGenerator, fieldClass, storage, depth, maxDepth);
        if (initial == null) {
            if (collection == null || collection.getClass().isAssignableFrom(List.class)) {
                return Collections.emptyList();
            } else if (collection.getClass().isAssignableFrom(Set.class)) {
                return Collections.emptySet();
            } else if (collection.getClass().isAssignableFrom(Queue.class)) {
                return new ArrayDeque<>();
            }
            return Collections.emptyList();
        }

        final Collection list = (collection == null)
                ? new ArrayList<>(size)
                : collection;
        list.add(initial);
        for (int i = 1; i < size; i++) {
            final Object t = generateValue(valueGenerator, fieldClass, storage, depth, maxDepth);
            list.add(t);
        }

        return list;
    }
}
