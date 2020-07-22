package io.dummymaker.export.experimental;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public class CsvConverter implements IConverter {

    public static final char DEFAULT_SEPARATOR = ',';

    /**
     * CSV format separator for values: value1,value2,value3 ...
     */
    private char separator = DEFAULT_SEPARATOR;

    /**
     * Flag to indicate wrap text (String) fields with quotes
     */
    private boolean wrapStringValue = false;
    /**
     * Generate header for CSV file
     */
    private boolean hasHeader = false;

    /**
     * @return exporter
     */
    public CsvConverter withStringWrap() {
        this.wrapStringValue = true;
        return this;
    }

    /**
     * @return exporter
     */
    public CsvConverter withHeader() {
        this.hasHeader = true;
        return this;
    }

    /**
     * @param separator char separator for CSV values
     * @return exporter
     */
    public CsvConverter withSeparator(final char separator) {
        this.separator = separator;
        return this;
    }

    @Override
    public <T> String convert(T t) {
        return null;
    }
}
