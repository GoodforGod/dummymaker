package io.dummymaker.export;

import io.dummymaker.annotation.complex.GenTime;
import java.lang.reflect.Field;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 7.3.2020
 */
final class TimeExportField extends ExportField {

    private final boolean isUnixTime;
    private final String formatter;

    TimeExportField(Field field, Type type, String exportName, GenTime annotation) {
        super(field, type, exportName);
        this.isUnixTime = false;
        this.formatter = (annotation == null)
                ? GenTime.DEFAULT_FORMAT
                : annotation.formatter();
    }

    String getFormatter() {
        return formatter;
    }

    boolean isUnixTime() {
        return isUnixTime;
    }
}
