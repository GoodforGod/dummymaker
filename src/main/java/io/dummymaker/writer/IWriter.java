package io.dummymaker.writer;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public interface IWriter {

    /**
     * Try to create file and setup writer
     *
     * @return operation result status
     */
    boolean init();

    /**
     * Writes string and add newline symbol to the end
     *
     * @param value string to write
     * @return indicates operation success
     */
    boolean writeLine(String value);

    /**
     * Closes write stream
     * @return indicates operation success
     */
    boolean flush();
}
