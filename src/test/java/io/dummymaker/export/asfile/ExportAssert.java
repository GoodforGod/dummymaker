package io.dummymaker.export.asfile;

import org.junit.After;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
abstract class ExportAssert extends Assert {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String fileToDelete;

    @After
    public void cleanFile() {
        try {
            if (fileToDelete != null) {
                final File file = new File(fileToDelete);
                Files.deleteIfExists(file.toPath());
                fileToDelete = null;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    void markFileForRemoval(String filename) {
        this.fileToDelete = filename;
    }

    protected String readFromFile(String filename) {
        markFileForRemoval(filename);
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("./" + filename), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
