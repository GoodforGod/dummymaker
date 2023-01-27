package io.dummymaker.export;

/**
 * Writer interface used by exporters to write values as files
 *
 * @author Anton Kurako (GoodforGod)
 * @see Exporter
 * @since 31.05.2017
 */
public interface Writer {

    /**
     * Writes string and add newline symbol to the end
     * 
     * @param value string to write
     */
    void write(String value);
}
