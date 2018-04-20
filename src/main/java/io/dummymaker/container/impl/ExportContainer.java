package io.dummymaker.container.impl;


import java.util.ArrayList;
import java.util.List;

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

    private final List<ExportContainer> containers = new ArrayList<>();

    private final String exportName;
    private final String exportValue;

    private final Type type;

    private ExportContainer(final String exportName,
                            final String exportValue,
                            final Type type) {
        this.exportName = exportName;
        this.exportValue = exportValue;
        this.type = (type == null) ? Type.VALUE : type;
    }

    public static ExportContainer buildValue(final String exportName,
                                             final String exportValue) {
        return new ExportContainer(exportName, exportValue, Type.VALUE);
    }

    public static ExportContainer buildEmbedded(final String exportName ) {
        return new ExportContainer(exportName, null, Type.EMBEDDED);
    }

    public static ExportContainer buildCollection(final String exportName ) {
        return new ExportContainer(exportName, null, Type.COLLECTION);
    }

    public static ExportContainer buildMap(final String exportName) {
        return new ExportContainer(exportName, null, Type.MAP);
    }

    public void add(ExportContainer container) {
        this.containers.add(container);
    }

    public List<ExportContainer> getContainers() {
        return containers;
    }

    public String getExportName() {
        return exportName;
    }

    public String getExportValue() {
        return exportValue;
    }

    public boolean isSimple() {
        return type.equals(Type.VALUE);
    }

    public boolean isEmbedded() {
        return type.equals(Type.EMBEDDED);
    }

    public boolean isCollection() {
        return type.equals(Type.COLLECTION);
    }

    public boolean isMap() {
        return type.equals(Type.MAP);
    }
}
