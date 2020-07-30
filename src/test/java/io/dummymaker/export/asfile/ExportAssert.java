package io.dummymaker.export.asfile;

import org.junit.After;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
abstract class ExportAssert extends Assert {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Set<String> fileToDelete = new HashSet<>();

    @After
    public void cleanFile() {
        fileToDelete.forEach(f -> {
            try {
                final File file = new File(f);
                Files.deleteIfExists(file.toPath());
                fileToDelete.remove(f);
            } catch (Exception e) {
                logger.warn(e.getMessage());
                fileToDelete.remove(f);
            }
        });
    }

    void markFileForRemoval(String filename) {
        this.fileToDelete.add(filename);
    }

    protected String readFromFile(String filename) {
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("./" + filename), StandardCharsets.UTF_8))) {
            markFileForRemoval(filename);
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
