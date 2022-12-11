package io.dummymaker.model.export;

import io.dummymaker.annotation.complex.GenTime;
import java.lang.reflect.Field;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 7.3.2020
 */
public class DateFieldContainer extends FieldContainer {

    private final boolean isUnixTime;
    private final String formatter;

    public DateFieldContainer(Field field, Type type, String exportName, GenTime annotation) {
        super(field, type, exportName);
        this.isUnixTime = false;
        this.formatter = (annotation == null)
                ? GenTime.DEFAULT_FORMAT
                : annotation.formatter();
    }

    public String getFormatter() {
        return formatter;
    }

    public boolean isUnixTime() {
        return isUnixTime;
    }
}
