package io.dummymaker.export;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Export objects in CSV format
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class CsvExporter<T> extends BaseExporter<T> {

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

    //<editor-fold desc="Constructors">

    public CsvExporter(final Class<T> primeClass) {
        this(primeClass, null);
    }

    public CsvExporter(final Class<T> primeClass,
                       final String path) {
        super(primeClass, path, ExportFormat.CSV);
    }

    public CsvExporter(final Class<T> primeClass,
                       final String path,
                       final boolean wrapTextValues,
                       final boolean generateHeader) {
        this(primeClass, path);
        this.wrapTextValues = wrapTextValues;
        this.generateHeader = generateHeader;
    }

    public CsvExporter(final Class<T> primeClass,
                       final String path,
                       final boolean wrapTextValues,
                       final boolean generateHeader,
                       final char separator) {
        this(primeClass, path, wrapTextValues, generateHeader);
        setSeparator(separator);
    }

    //</editor-fold>

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
        final Iterator<Map.Entry<String, String>> iterator = extractExportValues(t).entrySet().iterator();

        while (iterator.hasNext()) {
            final Map.Entry<String, String> obj = iterator.next();

            if(wrapTextValues && classContainer.finalFields().get(obj.getKey()).getType().equals(String.class))
                builder.append(wrapWithQuotes(obj.getValue()));
            else
                builder.append(obj.getValue());

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
        final Iterator<Map.Entry<String, Field>> iterator = classContainer.finalFields().entrySet().iterator();

        while (iterator.hasNext()) {
            header.append(iterator.next().getKey());

            if(iterator.hasNext())
                header.append(SEPARATOR);
        }
        return header.toString();
    }

    @Override
    public boolean export(final T t) {
        if(!isExportStateValid(t))
            return false;

        if (generateHeader)
            writeLine(generateCsvHeader());

        return writeLine(objectToCsv(t)) && flush();
    }

    @Override
    public boolean export(final List<T> list) {
        if(!isExportStateValid(list))
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

        for (final T t : list)
            result.append(objectToCsv(t));

        return result.toString();
    }
}
