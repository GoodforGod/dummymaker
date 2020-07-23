package io.dummymaker.model.export;

import io.dummymaker.annotation.complex.GenTime;

import java.lang.reflect.Field;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 7.3.2020
 */
public class DatetimeFieldContainer extends FieldContainer {

    private final boolean isUnixTime;
    private final String formatter;

    public DatetimeFieldContainer(Field field, Type type, String exportName, GenTime annotation) {
        super(field, type, exportName);
        this.isUnixTime = (annotation != null) && annotation.exportAsUnixTime();
        this.formatter = annotation.formatter();
    }

    public String getFormatter() {
        return formatter;
    }

    public boolean isUnixTime() {
        return isUnixTime;
    }
}
