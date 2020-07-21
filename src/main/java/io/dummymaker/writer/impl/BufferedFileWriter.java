package io.dummymaker.writer.impl;

import io.dummymaker.model.error.GenException;
import io.dummymaker.util.StringUtils;
import io.dummymaker.writer.IWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Buffered writer implementation
 *
 * @author GoodforGod
 * @see IWriter
 * @since 31.05.2017
 */
public class BufferedFileWriter implements IWriter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BufferedWriter writer;
    private final String path;

    /**
     * @param fileName  file name
     * @param path      path where to create file (NULL or UNKNOWN for home dir)
     * @param extension file extension
     */
    public BufferedFileWriter(final String fileName, final String path, final String extension, boolean append) {
        try {
            this.path = buildPath(fileName, path, extension);
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(this.path, append), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new GenException(e);
        }
    }

    private String buildPath(final String fileName, final String path, final String extension) {
        final String workPath = StringUtils.isBlank(path) ? "./" : path;
        return workPath + fileName + extension;
    }

    @Override
    public boolean isEmpty() {
        final File file = new File(path);
        return !file.exists() || file.length() == 0;
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
            logger.warn(e.getMessage());
            return false;
        }
    }
}
