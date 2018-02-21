package io.dummymaker.export.impl;

import io.dummymaker.export.container.ExportContainer;
import io.dummymaker.export.container.FieldContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Export objects in CSV format
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class CsvExporter<T> extends BasicExporter<T> {

    private final char DEFAULT_SEPARATOR = ',';

    /**
     * CSV format separator
     */
    private char SEPARATOR = DEFAULT_SEPARATOR;

    /**
     * Flag to indicate wrap text (String) fields with quotes
     */
    private boolean wrapTextValues = false;

    /**
     * Generate header for CSV file
     */
    private boolean generateHeader = false;

    public CsvExporter(final Class<T> primeClass) {
        this(primeClass, null);
    }

    public CsvExporter(final Class<T> primeClass,
                       final String path) {
        super(primeClass, path, ExportFormat.CSV, PresetStrategies.DEFAULT.getStrategy());
    }

    public CsvExporter(final Class<T> primeClass,
                       final String path,
                       final IStrategy strategy) {
        super(primeClass, path, ExportFormat.CSV, strategy);
    }

    public CsvExporter(final Class<T> primeClass,
                       final String path,
                       final IStrategy strategy,
                       final boolean wrapTextValues,
                       final boolean generateHeader) {
        this(primeClass, path, strategy);
        this.wrapTextValues = wrapTextValues;
        this.generateHeader = generateHeader;
    }
    /**
     * @param primeClass export class
     * @param path path where to export, 'null' for project HOME path
     * @param strategy naming strategy
     * @param wrapTextValues to force wrap string type field values with commas, like - 'string'
     * @param generateHeader generate header for export file, with field names as headers
     * @param separator csv format separator, default is comma ,
     */
    public CsvExporter(final Class<T> primeClass,
                       final String path,
                       final IStrategy strategy,
                       final boolean wrapTextValues,
                       final boolean generateHeader,
                       final char separator) {
        this(primeClass, path, strategy, wrapTextValues, generateHeader);
        setSeparator(separator);
    }

    private void setSeparator(final char separator) {
        this.SEPARATOR = (separator == ' ')
                ? DEFAULT_SEPARATOR
                : separator;
    }

    /**
     * Wraps text values (String) with quotes like 'value'
     * @param value values to wrap
     * @return wrapped values
     */
    private String wrapWithQuotes(final String value) {
        return "'" + value + "'";
    }

    private String objectToCsv(final T t) {
        final StringBuilder builder = new StringBuilder("");
        final Iterator<ExportContainer> iterator = extractExportValues(t).iterator();

        while (iterator.hasNext()) {
            final ExportContainer container = iterator.next();

            if(wrapTextValues && classContainer.getFieldByFinalName(container.getFieldName()).getType().equals(String.class))
                builder.append(wrapWithQuotes(container.getFieldValue()));
            else
                builder.append(container.getFieldValue());

            if (iterator.hasNext())
                builder.append(SEPARATOR);
        }

        return builder.toString();
    }

    /**
     * Generates header for CSV file
     * @return csv header
     */
    private String generateCsvHeader() {
        final StringBuilder header = new StringBuilder("");
        final Iterator<Map.Entry<String, FieldContainer>> iterator = classContainer.fieldContainerMap().entrySet().iterator();

        while (iterator.hasNext()) {
            header.append(iterator.next().getValue().getFinalFieldName());

            if(iterator.hasNext())
                header.append(SEPARATOR);
        }
        return header.toString();
    }

    @Override
    public boolean export(final T t) {
        if(!isExportStateValid(t) || !initWriter())
            return false;

        if (generateHeader)
            writeLine(generateCsvHeader());

        return writeLine(objectToCsv(t)) && flush();
    }

    @Override
    public boolean export(final List<T> list) {
        if(!isExportStateValid(list) || !initWriter())
            return false;

        if (generateHeader)
            writeLine(generateCsvHeader());

        for (final T t : list)
            writeLine(objectToCsv(t));

        return flush();
    }

    @Override
    public String exportAsString(final T t) {
        if(!isExportStateValid(t))
            return "";

        final StringBuilder result = new StringBuilder();

        if (generateHeader)
            result.append(generateCsvHeader()).append("\n");

        return result.append(objectToCsv(t)).toString();
    }

    @Override
    public String exportAsString(final List<T> list) {
        if(!isExportStateValid(list))
            return "";

        final StringBuilder result = new StringBuilder();

        if (generateHeader)
            result.append(generateCsvHeader()).append("\n");

        final Iterator<T> iterator = list.iterator();

        while (iterator.hasNext()) {
            final T t = iterator.next();
            result.append(objectToCsv(t));

            if(iterator.hasNext())
                result.append("\n");
        }

        return result.toString();
    }
}
