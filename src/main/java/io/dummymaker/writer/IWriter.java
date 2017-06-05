package io.dummymaker.writer;

import java.io.IOException;

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
     * @throws IOException when unable to write line
     */
    void writeLine(String value) throws IOException;

    /**
     * Closes write stream
     * @throws IOException when can't close buffer writter
     */
    void flush() throws IOException;
}
