package io.dummymaker.writer.impl;

import io.dummymaker.error.ExportException;
import io.dummymaker.util.StringUtils;
import io.dummymaker.writer.IWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Buffered writer implementation
 *
 * @author GoodforGod
 * @see IWriter
 * @since 31.05.2017
 */
public class FileWriter implements IWriter {

    private final String path;

    public FileWriter(String path, String filename) {
        this(path, filename, true);
    }

    /**
     * @param filename         file name
     * @param path             path where to create file (NULL or UNKNOWN for home
     *                         dir)
     * @param cleanFileIfExist clean file when writer is created
     */
    public FileWriter(String path, String filename, boolean cleanFileIfExist) {
        this.path = getPath(path, filename);
        if (cleanFileIfExist) {
            final File file = new File(path);
            if (file.exists())
                file.delete();
        }
    }

    private String getPath(String file, String path) {
        final String workPath = StringUtils.isBlank(path) ? "./" : path;
        return workPath + file;
    }

    @Override
    public boolean isEmpty() {
        final File file = new File(path);
        return !file.exists() || file.length() == 0;
    }

    @Override
    public boolean write(final String value) {
        if (StringUtils.isEmpty(value))
            return true;

        try (Writer writer = getWriter(true)) {
            writer.write(value);
            return true;
        } catch (IOException e) {
            throw new ExportException(e.getMessage(), e.getCause());
        }
    }

    private Writer getWriter(boolean append) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.path, append), StandardCharsets.UTF_8));
    }
}
