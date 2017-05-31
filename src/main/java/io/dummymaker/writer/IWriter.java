package io.dummymaker.writer;

import java.io.IOException;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public interface IWriter {

    void writeLine(String value) throws IOException;

    void flush() throws IOException;
}
