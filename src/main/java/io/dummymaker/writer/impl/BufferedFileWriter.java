package io.dummymaker.writer.impl;

import io.dummymaker.writer.IWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Buffered writer implementation
 *
 * @author GoodforGod
 * @see IWriter
 * @since 31.05.2017
 */
public class BufferedFileWriter implements IWriter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private BufferedWriter writer;

    /**
     * @param fileName  file name
     * @param path      path where to create file (NULL or UNKNOWN for home dir)
     * @param extension file extension
     * @throws IOException if can not instantiate writer
     */
    public BufferedFileWriter(final String fileName, final String path, final String extension)
            throws IOException {
        try {
            final String workPath = buildPath(fileName, path, extension);
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(workPath), "UTF-8")
            );
        } catch (IOException e) {
            logger.warn(e.getMessage() + " | CAN NOT CREATE BUFFERED WRITER.");
            throw e;
        }
    }

    private String buildPath(final String fileName, final String path, final String extension) {
        final String workPath = (path == null || path.trim().isEmpty())
                ? ""
                : path;

        return workPath + fileName + extension;
    }

    @Override
    public boolean write(final String value) {
        try {
            writer.write(value);
            return true;
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return flush();
        }
    }

    @Override
    public boolean flush() {
        try {
            if (writer != null)
                writer.close();
            return true;
        } catch (IOException e) {
            logger.warn(e.getMessage() + " | CAN NOT CLOSE WRITER");
            return false;
        }
    }
}
