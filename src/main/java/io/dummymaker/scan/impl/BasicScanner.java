package io.dummymaker.scan.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Basic scanner utility class
 *
 * @author GoodforGod
 * @since 17.07.2019
 */
abstract class BasicScanner {

    /**
     * Retrieve declared annotations from parent one and build set of them all
     *
     * @param annotation parent annotation
     * @return parent annotation and its declared ones
     */
    @NotNull
    protected List<Annotation> getAllAnnotations(final Annotation annotation) {
        final List<Annotation> list = Arrays.stream(annotation.annotationType().getDeclaredAnnotations())
                .collect(Collectors.toList());

        list.add(annotation);
        return list;
    }

    @NotNull
    protected List<Field> getValidFields(Class<?> target) {
        return getAllFields(target).stream()
                .filter(f -> !f.isSynthetic())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> !Modifier.isNative(f.getModifiers()))
                .filter(f -> !Modifier.isSynchronized(f.getModifiers()))
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
                .collect(Collectors.toList());
    }

    @NotNull
    protected List<Field> getAllFields(Class<?> target) {
        if (target == null || Object.class.equals(target))
            return Collections.emptyList();

        final List<Field> collected = Arrays.stream(target.getDeclaredFields())
                .collect(Collectors.toList());

        collected.addAll(getValidFields(target.getSuperclass()));
        return collected;
    }
}
