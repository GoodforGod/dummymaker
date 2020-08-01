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

    public static final String DEFAULT_PATH = "";

    private final String path;

    public FileWriter(String filename, boolean appendToFileIfExist) {
        this(DEFAULT_PATH, filename, appendToFileIfExist);
    }

    /**
     * @param filename            file name
     * @param path                path where to create file (NULL or EMPTY for home
     *                            dir)
     * @param appendToFileIfExist clean file when writer is created
     */
    public FileWriter(String path, String filename, boolean appendToFileIfExist) {
        this.path = getPath(path, filename);
        if (!appendToFileIfExist) {
            final File file = new File(this.path);
            if (file.exists())
                file.delete();
        }
    }

    private String getPath(String file, String path) {
        final String workPath = StringUtils.isBlank(path) ? DEFAULT_PATH : path;
        return workPath + file;
    }

    @Override
    public boolean write(final String value) {
        if (StringUtils.isEmpty(value))
            return true;

        try (Writer writer = getWriter()) {
            writer.append(value);
            return true;
        } catch (IOException e) {
            throw new ExportException(e.getMessage(), e.getCause());
        }
    }

    private Writer getWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.path, true), StandardCharsets.UTF_8));
    }
}
