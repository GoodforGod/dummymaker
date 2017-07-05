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
public class CsvExporter<T> extends OriginExporter<T> {

    private final char DEFAULT_SEPARATOR = ',';

    /**
     * CSV format separator
     */
    private char SEPARATOR = DEFAULT_SEPARATOR;

    /**
     * Flag to indicate wrap text (String) fields with quotes
     */
    private boolean wrapText = false;

    /**
     * Generate header for CSV file
     */
    private boolean withHeader = false;

    //<editor-fold desc="Constructors">

    public CsvExporter(final Class<T> primeClass) {
        this(primeClass, null);
    }

    public CsvExporter(final Class<T> primeClass,
                       final String path) {
        super(primeClass, path, ExportFormat.CSV);
    }

    public CsvExporter(final Class<T> primeClass,
                       final boolean wrapText,
                       final boolean withHeader) {
        this(primeClass);
        this.wrapText = wrapText;
        this.withHeader = withHeader;
    }

    public CsvExporter(final Class<T> primeClass,
                       final boolean wrapText,
                       final boolean withHeader,
                       final char separator) {
        this(primeClass, wrapText, withHeader);
        setSeparator(separator);
    }

    public CsvExporter(final Class<T> primeClass,
                       final String path,
                       final boolean wrapText,
                       final boolean withHeader) {
        this(primeClass, path);
        this.wrapText = wrapText;
        this.withHeader = withHeader;
    }

    public CsvExporter(final Class<T> primeClass,
                       final String path,
                       final boolean wrapText,
                       final boolean withHeader,
                       final char separator) {
        this(primeClass, path, wrapText, withHeader);
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
        final Iterator<Map.Entry<String, String>> iterator = getExportValues(t).entrySet().iterator();

        while (iterator.hasNext()) {

            final Map.Entry<String, String> obj = iterator.next();
            if(wrapText && exportFields.get(obj.getKey()).getType().equals(String.class))
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
        final Iterator<Map.Entry<String, Field>> iterator = exportFields.entrySet().iterator();

        while (iterator.hasNext()) {
            header.append(iterator.next().getKey());

            if(iterator.hasNext())
                header.append(SEPARATOR);
        }
        return header.toString();
    }

    @Override
    public boolean export(final T t) {
        if(t == null)
            return false;

        if (withHeader)
            writeLine(generateCsvHeader());

        return writeLine(objectToCsv(t)) && flush();
    }

    @Override
    public boolean export(final List<T> list) {
        if(list == null || list.isEmpty())
            return false;

        if (withHeader)
            writeLine(generateCsvHeader());

        for (T t : list)
            writeLine(objectToCsv(t));
        return flush();
    }

    @Override
    public String exportAsString(T t) {
        if(t == null)
            return "";

        final StringBuilder result = new StringBuilder();

        if (withHeader)
            result.append(generateCsvHeader());

        return result.append(objectToCsv(t)).toString();
    }

    @Override
    public String exportAsString(List<T> list) {
        if(list == null || list.isEmpty())
            return "";

        final StringBuilder result = new StringBuilder();
        if (withHeader)
            result.append(generateCsvHeader());

        for (T t : list)
            result.append(objectToCsv(t));
        return result.toString();
    }
}
