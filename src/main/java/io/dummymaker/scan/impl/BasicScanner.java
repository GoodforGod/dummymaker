package io.dummymaker.scan.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    protected List<Annotation> getAllDeclaredAnnotations(final Annotation annotation) {
        final List<Annotation> list = Arrays.stream(annotation.annotationType().getDeclaredAnnotations())
                .collect(Collectors.toList());

        list.add(annotation);
        return list;
    }

    protected List<Field> getAllDeclaredFields(Class tClass) {
        if (tClass == null || Object.class.equals(tClass))
            return Collections.emptyList();

        final List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(tClass.getDeclaredFields()));
        fields.addAll(getAllDeclaredFields(tClass.getSuperclass()));

        return fields;
    }
}
