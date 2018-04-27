package io.dummymaker.container.impl;


/**
 * ExportContainer class for BasicExporter extract class method
 *
 * @see io.dummymaker.export.IExporter
 *
 * @author GoodforGod
 * @since 03.09.2017
 */
public class ExportContainer {

    public enum Type {
        VALUE,
        EMBEDDED,
        COLLECTION,
        MAP
    }

    private final String exportName;
    private final String exportValue;

    private ExportContainer(final String exportName,
                            final String exportValue,
                            final Type type) {
        this.exportName = exportName;
        this.exportValue = exportValue;
    }

    public static ExportContainer asValue(final String exportName,
                                          final String exportValue) {
        return new ExportContainer(exportName, exportValue, Type.VALUE);
    }

    public String getExportName() {
        return exportName;
    }

    public String getExportValue() {
        return exportValue;
    }
}
