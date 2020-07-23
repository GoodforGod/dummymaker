package io.dummymaker.model.export;

/**
 * ExportContainer class for BasicExporter extract class method
 *
 * @author GoodforGod
 * @see io.dummymaker.export.IExporter
 * @since 03.09.2017
 */
public class ExportContainer {

    private final String exportName;
    private final String exportValue;
    private FieldContainer.Type type;

    private ExportContainer(final String exportName,
                            final String exportValue,
                            final FieldContainer.Type type) {
        this.exportName = exportName;
        this.exportValue = exportValue;
        this.type = type;
    }

    public static ExportContainer asValue(final String exportName,
                                          final String exportValue) {
        return new ExportContainer(exportName, exportValue, FieldContainer.Type.SIMPLE);
    }

    public static ExportContainer asDatetime(final String exportName,
                                             final String exportValue) {
        return new ExportContainer(exportName, exportValue, FieldContainer.Type.DATE);
    }

    public static ExportContainer asList(final String exportName,
                                         final String exportValue) {
        return new ExportContainer(exportName, exportValue, FieldContainer.Type.COLLECTION);
    }

    public static ExportContainer asArray(final String exportName,
                                          final String exportValue) {
        return new ExportContainer(exportName, exportValue, FieldContainer.Type.ARRAY);
    }

    public static ExportContainer asArray2D(final String exportName,
                                            final String exportValue) {
        return new ExportContainer(exportName, exportValue, FieldContainer.Type.ARRAY_2D);
    }

    public static ExportContainer asMap(final String exportName,
                                        final String exportValue) {
        return new ExportContainer(exportName, exportValue, FieldContainer.Type.MAP);
    }

    public String getExportName() {
        return exportName;
    }

    public String getExportValue() {
        return exportValue;
    }

    public FieldContainer.Type getType() {
        return type;
    }
}
