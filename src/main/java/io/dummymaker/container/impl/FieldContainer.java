package io.dummymaker.container.impl;

import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.util.BasicStringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Used by ClassContainer to contain field value, and final field name
 *
 * @see ClassContainer
 *
 * @author GoodforGod
 * @since 03.09.2017
 */
public class FieldContainer {

    private enum Type {
        SIMPLE,
        ENUMERABLE,
        EMBEDDED,
        COLLECTION
    }

    /** Final field name (renamed or converted by naming strategy) */
    private final String exportName;

    /** Is field enumerable or not */
    private final Type type;

    private FieldContainer(final String exportName,
                           final Type type) {
        this.exportName = exportName;
        this.type = type;
    }

    public static FieldContainer as(final Field field,
                                    final Annotation annotation,
                                    final String exportName) {
        Type type = null;
        if (field.getType().equals(Set.class)
                || field.getType().equals(List.class)
                || field.getType().equals(Map.class))
            type = Type.COLLECTION;

        if (annotation != null) {
            if (annotation.annotationType().equals(GenEmbedded.class)) {
                type = Type.EMBEDDED;
            } else if (annotation.annotationType().equals(GenEnumerate.class)) {
                type = Type.ENUMERABLE;
            }
        }

        if (type == null)
            type = Type.SIMPLE;

        final String finalName = (BasicStringUtils.isEmpty(exportName))
                ? field.getName()
                : exportName;

        return new FieldContainer(finalName, type);
    }

    public boolean isSimple() {
        return type.equals(Type.SIMPLE);
    }

    public boolean isEmbedded() {
        return type.equals(Type.EMBEDDED);
    }

    public boolean isCollection() {
        return type.equals(Type.COLLECTION);
    }

    public boolean isEnumerable() {
        return type.equals(Type.ENUMERABLE);
    }

    public String getExportName() {
        return exportName;
    }
}
