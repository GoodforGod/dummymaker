package io.dummymaker.writer;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public interface IWriter {

    /**
     * Writes string and add newline symbol to the end
     *
     * @param value string to write
     */
    void writeLine(String value);

    /**
     * Closes write stream
     */
    void flush();
}
