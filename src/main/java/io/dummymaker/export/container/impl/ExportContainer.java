package io.dummymaker.export.container.impl;

import io.dummymaker.export.impl.BasicExporter;

/**
 * ExportContainer class for BasicExporter extract class method
 *
 * @see BasicExporter
 *
 * @author GoodforGod
 * @since 03.09.2017
 */
public class ExportContainer {

    private final String finalFieldName;
    private final String fieldValue;

    public ExportContainer(String finalFieldName, String fieldValue) {
        this.finalFieldName = finalFieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return finalFieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
