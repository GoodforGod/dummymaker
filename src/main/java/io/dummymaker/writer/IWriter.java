package io.dummymaker.writer;

/**
 * Writer interface used by exporters to write values as files
 *
 * @author GoodforGod
 * @see io.dummymaker.export.IExporter
 * @since 31.05.2017
 */
public interface IWriter {

    boolean isEmpty();

    /**
     * Writes string and add newline symbol to the end
     *
     * @param value string to write
     * @return indicates operation success
     */
    boolean write(String value);

    /**
     * Appends string and add newline symbol to the end
     *
     * @param value string to write
     * @return indicates operation success
     */
    boolean append(String value);
}
