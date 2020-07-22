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

    /**
     * @param fileName  file name
     * @param path      path where to create file (NULL or UNKNOWN for home dir)
     * @param extension file extension
     */
    public FileWriter(final String fileName, final String path, final String extension, boolean append) {
        this.path = getPath(fileName, path, extension);
    }

    private String getPath(final String fileName, final String path, final String extension) {
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
        try(Writer writer = getWriter(false)) {
            writer.write(value);
            return true;
        } catch (IOException e) {
            throw new ExportException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean append(String value) {
        try(Writer writer = getWriter(true)) {
            writer.append(value);
            return true;
        } catch (IOException e) {
            throw new ExportException(e.getMessage(), e.getCause());
        }
    }

    private Writer getWriter(boolean append) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.path, append), StandardCharsets.UTF_8));
    }
}
