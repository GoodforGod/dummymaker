package io.goodforgod.dummymaker.export;

/**
 * Writer interface used by exporters to write values
 *
 * @author Anton Kurako (GoodforGod)
 * @see Exporter
 * @since 31.05.2017
 */
public interface Writer extends AutoCloseable {

    /**
     * Writes string and add newline symbol to the end
     * 
     * @param value string to write
     */
    void write(String value);
}
