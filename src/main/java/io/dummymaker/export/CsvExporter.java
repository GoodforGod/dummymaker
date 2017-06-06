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

    public CsvExporter(Class<T> primeClass) {
        this(primeClass, null);
    }

    public CsvExporter(Class<T> primeClass, boolean wrapText, boolean withHeader) {
        this(primeClass, null);
        this.wrapText = wrapText;
        this.withHeader = withHeader;
    }

    public CsvExporter(Class<T> primeClass, boolean wrapText, boolean withHeader, char separator) {
        this(primeClass, null);
        this.wrapText = wrapText;
        this.withHeader = withHeader;
        setSeparator(separator);
    }

    public CsvExporter(Class<T> primeClass, String path) {
        super(primeClass, path, ExportFormat.CSV);
    }

    public CsvExporter(Class<T> primeClass, String path, boolean wrapText, boolean withHeader) {
        this(primeClass, path);
        this.wrapText = wrapText;
        this.withHeader = withHeader;
    }

    public CsvExporter(Class<T> primeClass, String path, boolean wrapText, boolean withHeader, char separator) {
        this(primeClass, path);
        this.wrapText = wrapText;
        this.withHeader = withHeader;
        setSeparator(separator);
    }

    //</editor-fold>

    public void setSeparator(char separator) {
        this.SEPARATOR = (separator == ' ')
                ? DEFAULT_SEPARATOR
                : separator;
    }

    public void setWrapText(boolean wrapText) {
        this.wrapText = wrapText;
    }

    public void setWithHeader(boolean withHeader) {
        this.withHeader = withHeader;
    }

    /**
     * Wraps text values (String) with quotes like 'value'
     * @param value values to wrap
     * @return wrapped values
     */
    private String wrapWithQuotes(String value) {
        return "'" + value + "'";
    }

    private String objectToCsv(T t) {
        StringBuilder builder = new StringBuilder("");

        Iterator<Map.Entry<String, String>> iterator = getExportValues(t).entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<String, String> obj = iterator.next();
            if(wrapText && fieldsToExport.get(obj.getKey()).getType().equals(String.class))
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
        String header = "";
        Iterator<Map.Entry<String, Field>> iterator = fieldsToExport.entrySet().iterator();

        while (iterator.hasNext()) {
            header += iterator.next().getKey();

            if(iterator.hasNext())
                header += SEPARATOR;
        }

        return header;
    }

    @Override
    public void export(T t) {
        if (withHeader)
            writeLine(generateCsvHeader());

        writeLine(objectToCsv(t));
        flush();
    }

    @Override
    public void export(List<T> tList) {
        if (withHeader)
            writeLine(generateCsvHeader());

        for (T t : tList)
            writeLine(objectToCsv(t));
        flush();
    }
}
