package io.dummymaker.container.impl;

import io.dummymaker.generator.complex.impl.*;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.generator.simple.impl.SequenceGenerator;

import java.lang.reflect.Field;

import static io.dummymaker.util.BasicStringUtils.isEmpty;

/**
 * Used by ClassContainer to contain field value, and final field name
 *
 * @author GoodforGod
 * @see ClassContainer
 * @since 03.09.2017
 */
public class FieldContainer {

    /**
     * Final field name (renamed or converted by naming strategy)
     */
    private final String exportName;
    /**
     * Is field enumerable or not
     */
    private final Type type;

    private FieldContainer(final String exportName,
                           final Type type) {
        this.exportName = exportName;
        this.type = type;
    }

    public static FieldContainer as(final Field field,
                                    final Class<? extends IGenerator> generator,
                                    final String exportName) {
        final Type type = getType(generator);
        final String finalName = (isEmpty(exportName) && field != null)
                ? field.getName()
                : exportName;

        return new FieldContainer(finalName, type);
    }

    private static Type getType(final Class<? extends IGenerator> generator) {
        if (generator == null)
            return Type.SIMPLE;

        if (generator.equals(SetComplexGenerator.class) || generator.equals(ListComplexGenerator.class)) {
            return Type.COLLECTION;
        } else if (generator.equals(MapComplexGenerator.class)) {
            return Type.MAP;
        } else if (generator.equals(EmbeddedGenerator.class)) {
            return Type.EMBEDDED;
        } else if (generator.equals(SequenceGenerator.class)) {
            return Type.SEQUENTIAL;
        } else if (generator.equals(ArrayComplexGenerator.class)) {
            return Type.ARRAY;
        } else if (generator.equals(Array2DComplexGenerator.class)) {
            return Type.ARRAY_2D;
        }

        return Type.SIMPLE;
    }

    public Type getType() {
        return type;
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

    public boolean isSequential() {
        return type.equals(Type.SEQUENTIAL);
    }

    public boolean isArray() {
        return type.equals(Type.ARRAY);
    }

    public boolean isArray2D() {
        return type.equals(Type.ARRAY_2D);
    }

    public String getExportName() {
        return exportName;
    }

    public enum Type {
        SIMPLE,
        SEQUENTIAL,
        EMBEDDED,
        COLLECTION,
        MAP,
        ARRAY,
        ARRAY_2D
    }
}
