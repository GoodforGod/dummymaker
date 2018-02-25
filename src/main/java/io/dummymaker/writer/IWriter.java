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
     * @return indicates operation success
     */
    boolean write(String value);

    /**
     * Closes write stream
     * @return indicates operation success
     */
    boolean flush();
}
