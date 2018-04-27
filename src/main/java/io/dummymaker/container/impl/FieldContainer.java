package io.dummymaker.container.impl;

import io.dummymaker.generator.complex.impl.ListComplexGenerator;
import io.dummymaker.generator.complex.impl.MapComplexGenerator;
import io.dummymaker.generator.complex.impl.SetComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.generator.simple.impl.EnumerateGenerator;

import java.lang.reflect.Field;

import static io.dummymaker.util.BasicStringUtils.isEmpty;

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

        if ((generator.equals(SetComplexGenerator.class)
                || generator.equals(ListComplexGenerator.class)
                || generator.equals(MapComplexGenerator.class)))
            return Type.COLLECTION;

        if (generator.equals(EmbeddedGenerator.class)) {
            return Type.EMBEDDED;
        } else if (generator.equals(EnumerateGenerator.class)) {
            return Type.ENUMERABLE;
        }

        return Type.SIMPLE;
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
