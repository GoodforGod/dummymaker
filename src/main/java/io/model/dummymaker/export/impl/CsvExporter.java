package io.model.dummymaker.export.impl;

import io.model.dummymaker.export.ExportType;
import io.model.dummymaker.export.OriginExporter;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class CsvExporter<T> extends OriginExporter<T> {

    private final char DEFAULT_SEPARATOR = ';';

    private char SEPARATOR = DEFAULT_SEPARATOR;

    //<editor-fold desc="Constructors">

    public CsvExporter(Class<T> primeClass) {
        super(primeClass, ExportType.JSON);
    }

    public CsvExporter(Class<T> primeClass, char separator) {
        super(primeClass, ExportType.JSON);
        SEPARATOR = (separator == ' ') ? DEFAULT_SEPARATOR : separator;
    }

    public CsvExporter(Class<T> primeClass, String path) {
        super(primeClass, path, ExportType.JSON);
    }

    public CsvExporter(Class<T> primeClass, String path, char separator) {
        super(primeClass, path, ExportType.JSON);
        SEPARATOR = (separator == ' ') ? DEFAULT_SEPARATOR : separator;
    }

    //</editor-fold>

    private String followCSVFormat(String value) {
        return (value.contains("\""))
                ? value.replace("\"", "\"\"")
                : value;
    }

    private StringBuilder objectToCsv(T t) {
        StringBuilder builder = new StringBuilder("");

        return builder;
    }

    @Override
    public void export(T t) {

    }

    @Override
    public void export(List<T> t) {
        t.forEach(this::export);
    }
}
