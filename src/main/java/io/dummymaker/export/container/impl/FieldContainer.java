package io.dummymaker.export.container.impl;

import java.lang.reflect.Field;

/**
 * Used by ClassContainer to contain field value, and final field name
 *
 * @see ClassContainer
 *
 * @author GoodforGod
 * @since 03.09.2017
 */
public class FieldContainer {

    /**
     * Class Field
     */
    private final Field field;

    /**
     * Final field name (renamed or converted by naming strategy)
     */
    private final String exportName;

    /**
     * Is field enumerable or not
     *
     * @see io.dummymaker.annotation.special.GenEnumerate
     */
    private final boolean isEnumerable;

    FieldContainer(Field field, String finalFieldName, boolean isEnumerable) {
        this.field = field;
        this.exportName = finalFieldName;
        this.isEnumerable = isEnumerable;
    }

    public boolean isEnumerable() {
        return isEnumerable;
    }

    public Field getField() {
        return field;
    }

    public String getExportName() {
        return exportName;
    }
}
