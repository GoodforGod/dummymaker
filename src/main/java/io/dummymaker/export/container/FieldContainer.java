package io.dummymaker.export.container;

import java.lang.reflect.Field;

/**
 * Used by BaseClassContainer to contain field value, and final field name
 *
 * @see BaseClassContainer
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
    private final String finalFieldName;

    public FieldContainer(Field field, String finalFieldName) {
        this.field = field;
        this.finalFieldName = finalFieldName;
    }

    public Field getField() {
        return field;
    }

    public String getFinalFieldName() {
        return finalFieldName;
    }
}
