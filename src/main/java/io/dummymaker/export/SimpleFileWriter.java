package io.dummymaker.export;

import io.dummymaker.error.GenExportException;
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

    private final java.io.Writer writer;

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

        final String filePath;
        if (path == null || StringUtils.isBlank(path)) {
            filePath = filename;
        } else if (path.endsWith("/")) {
            filePath = path + filename;
        } else {
            filePath = path + "/" + filename;
        }

        try {
            this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, appendFile), charset));
        } catch (FileNotFoundException e) {
            throw new GenExportException("File not found while writing: ", e);
        }
    }

    @Override
    public void write(final String value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }

        try {
            writer.append(value);
        } catch (IOException e) {
            throw new GenExportException("Exception occurred while writing: ", e);
        }
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}
