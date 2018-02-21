package io.dummymaker.export.container;

/**
 * ExportContainer class for BaseExporter extract class method
 *
 * @see io.dummymaker.export.BaseExporter
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
