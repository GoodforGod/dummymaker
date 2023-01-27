package io.dummymaker.export;

import io.dummymaker.error.ExportException;
import io.dummymaker.util.StringUtils;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Buffered writer implementation
 *
 * @author Anton Kurako (GoodforGod)
 * @see Writer
 * @since 31.05.2017
 */
public final class SimpleFileWriter implements Writer {

    private static final String DEFAULT_PATH = "";

    private final boolean appendFile;
    private final String path;
    private final Charset charset;

    SimpleFileWriter(boolean appendFile, @NotNull String filename) {
        this(appendFile, filename, DEFAULT_PATH);
    }

    SimpleFileWriter(boolean appendFile, @NotNull String filename, @Nullable String path) {
        this(appendFile, filename, path, StandardCharsets.UTF_8);
    }

    /**
     * @param filename   file name
     * @param path       path where to create file (NULL or EMPTY for home dir)
     * @param appendFile True to append existing file, False to rewrite
     * @param charset    to use when writing to File
     */
    SimpleFileWriter(boolean appendFile, @NotNull String filename, @Nullable String path, @NotNull Charset charset) {
        if (StringUtils.isBlank(filename)) {
            throw new IllegalArgumentException("File name can't be empty");
        }

        this.appendFile = appendFile;
        this.charset = charset;
        if (path == null || StringUtils.isBlank(path)) {
            this.path = filename;
        } else if (path.endsWith("/")) {
            this.path = path + filename;
        } else {
            this.path = path + "/" + filename;
        }
    }

    @Override
    public void write(final String value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }

        try (java.io.Writer writer = getWriter()) {
            writer.append(value);
        } catch (IOException e) {
            throw new ExportException(e);
        }
    }

    private java.io.Writer getWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, appendFile), charset));
    }
}
