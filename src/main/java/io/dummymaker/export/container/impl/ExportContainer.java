package io.dummymaker.export.container.impl;


/**
 * ExportContainer class for BasicExporter extract class method
 *
 * @see io.dummymaker.export.impl.BasicExporter
 *
 * @author GoodforGod
 * @since 03.09.2017
 */
public class ExportContainer {

    /**
     * Export field name
     */
    private final String exportName;

    /**
     * Export field value
     */
    private final String exportValue;

    public ExportContainer(String exportFieldName, String fieldValue) {
        this.exportName = exportFieldName;
        this.exportValue = fieldValue;
    }

    public String getExportName() {
        return exportName;
    }

    public String getExportValue() {
        return exportValue;
    }
}
