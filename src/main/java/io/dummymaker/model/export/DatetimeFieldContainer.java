package io.dummymaker.model.export;

import io.dummymaker.annotation.complex.GenTime;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 7.3.2020
 */
public class DatetimeFieldContainer extends FieldContainer {

    private final boolean isUnixTime;

    public DatetimeFieldContainer(Type type, String exportName, GenTime annotation) {
        super(type, exportName);
        this.isUnixTime = (annotation != null) && annotation.exportAsUnixTime();
    }

    public boolean isUnixTime() {
        return isUnixTime;
    }
}
