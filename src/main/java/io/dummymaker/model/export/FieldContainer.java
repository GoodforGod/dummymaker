package io.dummymaker.model.export;

import io.dummymaker.generator.complex.*;
import io.dummymaker.generator.simple.time.*;

/**
 * Used by ClassContainer to contain field value, and final field name
 *
 * @author GoodforGod
 * @see ClassContainer
 * @since 03.09.2017
 */
public class FieldContainer {

    public enum Type {
        SIMPLE,
        DATETIME,
        SEQUENTIAL,
        EMBEDDED,
        COLLECTION,
        MAP,
        ARRAY,
        ARRAY_2D
    }

    /**
     * Final field name (renamed or converted by naming strategy)
     */
    private final String exportName;

    /**
     * Is field enumerable or not
     */
    private final Type type;

    public FieldContainer(Type type, String exportName) {
        this.exportName = exportName;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public boolean isSimple() {
        return type.equals(Type.SIMPLE);
    }

    public boolean isDatetime() {
        return type.equals(Type.DATETIME);
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
}
