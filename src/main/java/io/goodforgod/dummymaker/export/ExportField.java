package io.goodforgod.dummymaker.export;

import java.lang.reflect.Field;

/**
 * Used by ClassContainer to contain field value, and final field name
 *
 * @author Anton Kurako (GoodforGod)
 * @since 03.09.2017
 */
class ExportField {

    enum Type {
        STRING,
        NUMBER,
        BOOLEAN,
        DATE,
        COMPLEX,
        COLLECTION,
        MAP,
        ARRAY,
        ARRAY_2D
    }

    /**
     * Final field name (renamed or converted by naming strategy)
     */
    private final String exportName;
    private final Type type;
    private final Field field;

    ExportField(Field field, Type type, String exportName) {
        this.field = field;
        this.exportName = exportName;
        this.type = type;
    }

    public Field getField() {
        return field;
    }

    public Type getType() {
        return type;
    }

    public boolean isSimple() {
        return type.equals(Type.STRING) || type.equals(Type.NUMBER) || type.equals(Type.BOOLEAN);
    }

    public boolean isDatetime() {
        return type.equals(Type.DATE);
    }

    public boolean isEmbedded() {
        return type.equals(Type.COMPLEX);
    }

    public boolean isCollection() {
        return type.equals(Type.COLLECTION);
    }

    public boolean isArray() {
        return type.equals(Type.ARRAY);
    }

    public boolean isArray2D() {
        return type.equals(Type.ARRAY_2D);
    }

    public String getName() {
        return exportName;
    }
}
