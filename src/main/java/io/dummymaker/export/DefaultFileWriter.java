package io.dummymaker.export;

import io.dummymaker.error.ExportException;
import io.dummymaker.util.StringUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Buffered writer implementation
 *
 * @author Anton Kurako (GoodforGod)
 * @see Writer
 * @since 31.05.2017
 */
final class DefaultFileWriter implements Writer {

    public static final String DEFAULT_PATH = "";

    private final String path;

    DefaultFileWriter(String filename, boolean appendFile) {
        this(DEFAULT_PATH, filename, appendFile);
    }

    /**
     * @param filename   file name
     * @param path       path where to create file (NULL or EMPTY for home dir)
     * @param appendFile if true than reuse existing file
     */
    DefaultFileWriter(String path, String filename, boolean appendFile) {
        this.path = getPath(path, filename);
        if (!appendFile) {
            try {
                final File file = new File(this.path);
                Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                throw new ExportException("File '" + path + filename + "' can not be deleted!");
            }
        }
    }

    private String getPath(String file, String path) {
        final String workPath = StringUtils.isBlank(path)
                ? DEFAULT_PATH
                : path;
        return workPath + file;
    }

    @Override
    public boolean write(final String value) {
        if (StringUtils.isEmpty(value))
            return true;

        try (java.io.Writer writer = getWriter()) {
            writer.append(value);
            return true;
        } catch (IOException e) {
            throw new ExportException(e);
        }
    }

    private java.io.Writer getWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.path, true), StandardCharsets.UTF_8));
    }
}
